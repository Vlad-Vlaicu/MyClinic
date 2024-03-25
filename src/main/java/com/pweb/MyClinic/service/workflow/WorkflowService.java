package com.pweb.MyClinic.service.workflow;

import com.pweb.MyClinic.mappers.AccountInfoMapper;
import com.pweb.MyClinic.mappers.PaymentInfoMapper;
import com.pweb.MyClinic.mappers.TicketInfoMapper;
import com.pweb.MyClinic.model.TicketStatus;
import com.pweb.MyClinic.model.db.Product;
import com.pweb.MyClinic.model.db.Ticket;
import com.pweb.MyClinic.service.company.*;
import com.pweb.MyClinic.service.exception.ServiceException;
import com.pweb.MyClinic.service.security.JwtService;
import com.pweb.MyClinic.service.security.UserService;
import com.pweb.model.generated.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.pweb.MyClinic.helper.TimeHelper.TIME_FORMATTER;
import static com.pweb.MyClinic.helper.UserHelper.*;
import static com.pweb.MyClinic.model.PaymentStatus.PAID;
import static com.pweb.MyClinic.model.Role.MEDIC;
import static com.pweb.MyClinic.model.TicketStatus.*;
import static com.pweb.MyClinic.service.error.ServiceError.*;
import static com.pweb.model.generated.PaymentType.CNAS;
import static java.math.RoundingMode.HALF_UP;
import static java.time.LocalDateTime.now;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkflowService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final ClientInfoService clientInfoService;
    private final EmployeeInfoService employeeInfoService;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final TicketService ticketService;

    public AuthResponse login(LoginRequest request) {
        try {
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            if (authentication.isAuthenticated()) {
                var response = new AuthResponse();
                response.setAccessToken(jwtService.generateToken(request.getEmail()));
                response.setTokenType("Bearer");
                return response;
            }
        } catch (BadCredentialsException e) {
            throw new ServiceException(LOGIN_FAILED);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public AuthResponse registerUser(RegisterRequest request) {
        var userInfo = generateUser(request);

        try {
            var savedUser = userService.addUser(userInfo);
            var clientInfo = generateClientInfo(savedUser.getId(), request);
            clientInfoService.saveClientInfo(clientInfo);

        } catch (Exception e) {
            throw new ServiceException(REGISTER_FAILED);
        }

        var loginRequest = new LoginRequest().email(request.getEmail()).password(request.getPassword());
        return login(loginRequest);
    }

    public AccountInfo getAccountInfo() {
        var clientInfo = clientInfoService.getAccountInfo(getUserId());
        var accountInfo = AccountInfoMapper.INSTANCE.mapClientInfoToAccountInfo(clientInfo);
        var tickets = getUserTickets(getUserId()).stream().map(TicketInfoMapper.INSTANCE::mapTicketToTicketInfo).toList();
        accountInfo.setTickets(tickets);
        return accountInfo;
    }

    public List<TicketInfo> getUserTickets(){
        return getUserTickets(getUserId()).stream().map(TicketInfoMapper.INSTANCE::mapTicketToTicketInfo).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public String createTicket(CreateTicketRequest request) {
        var ticket = generateTicket(request);
        var paymentInfo = generatePaymentInfo(request, getProduct(request.getProductId().setScale(0, HALF_UP).longValue()));
        ticket.setClientId((long) getUserId());
        paymentInfo.setClientId((long) getUserId());
        ticket.setProductId((request.getProductId().setScale(0, HALF_UP).longValue()));

        try {
            var savedPayment = paymentService.savePayment(paymentInfo);
            ticket.setPaymentId(savedPayment.getId());
            var savedTicket = ticketService.saveTicket(ticket);
            return savedTicket.getId().toString();
        } catch (Exception e) {
            throw new ServiceException(TICKET_CREATION_FAILED);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public com.pweb.model.generated.PaymentInfo payTicket(Long ticketId){
        var ticket = ticketService.getTicketById(ticketId);
        var paymentId = ticket.getPaymentId();
        var payment = paymentService.getPaymentInfo(paymentId);

        if(Objects.equals(payment.getPaymentType(), CNAS.toString())){
            throw new ServiceException(TICKET_PAYMENT_ERROR_WRONG_PAYMENT_TYPE);
        }

        payment.setStatus(PAID);
        payment.setExecutionTime(now().format(TIME_FORMATTER));
        paymentService.savePayment(payment);
        return PaymentInfoMapper.INSTANCE.paymentInfoDBToPaymentInfoGen(payment);
    }

    @Transactional
    public void addEmployee(AddEmployeeRequest request){
        var userInfo = generateEmployeeUser(request);

        try {
            var savedUser = userService.addUser(userInfo);
            var employeeInfo = generateEmployeeInfo(savedUser.getId(), request);
            employeeInfoService.saveEmployeeInfo(employeeInfo);

        } catch (Exception e) {
            throw new ServiceException(CREATE_EMPLOYEE_FAILED);
        }
    }

    public Product getProduct(Long id) {
        return productService.getProduct(id);
    }

    private Integer getUserId() {
        var username = jwtService.extractUsername();
        var user = userService.loadUserByUsername(username);
        return user.getId();
    }

    public List<Ticket> getUserTickets (Integer userId){
        return ticketService.getUserTickets((long) userId);
    }

    public void cancelProcessing (Long ticketId){
        ticketService.releaseTicket(ticketId, CREATED);
    }

    public void cancelTicket(Long ticketId){
        ticketService.cancelTicket(ticketId, getUserId().longValue());
    }

    public void finishTicket(Long ticketId){
        ticketService.finishTicket(ticketId);
    }

    public TicketInfo claimTicket(){
        return TicketInfoMapper.INSTANCE.mapTicketToTicketInfo(ticketService.claimTicket(Long.parseLong(getUserId().toString())));
    }

    public void completeProcessing(Long ticketId, TicketInfo ticketInfo){
        if (ticketId != ticketInfo.getId().longValue()){
            throw new ServiceException(TICKET_IDS_DONT_MATCH);
        }

        var doctorUser = userService.getUserById(ticketInfo.getDoctorId().longValue());
        if (doctorUser.isEmpty() || !doctorUser.get().getRole().equals(MEDIC)){
            throw new ServiceException(DOCTOR_SELECTED_IS_NOT_MEDIC);
        }

        var ticket = ticketService.getTicketById(ticketId);
        if (ticket.getTicketStatus() != PROCESSING){
            log.info("Ticket was expected to have status processing, but was {}", ticket.getTicketStatus().toString());
            throw new ServiceException(INVALID_TICKET_STATUS);
        }

        if (ticket.getEmployeeId() != getUserId().longValue()){
            log.info("Ticket is already being processed");
            throw new ServiceException(TICKET_IS_ALREADY_BEING_PROCESSED);
        }

        ticket.setEmployeeId(getUserId().longValue());
        ticket.setTicketStatus(IN_PROGRESS);
        ticket.setReservedTime(ticketInfo.getPeriod());
        ticket.setProductId(ticketInfo.getProductId().longValue());
        ticket.setDoctorId(ticketInfo.getDoctorId().longValue());

        ticketService.updateTicket(ticket);
    }

    public TicketInfo getTicketById (Long ticketId){
        var ticket = ticketService.getTicketById(ticketId);
        return TicketInfoMapper.INSTANCE.mapTicketToTicketInfo(ticket);
    }

    public TicketInfo getUserTicketById(Long ticketId){
        var ticket = ticketService.getTicketById(ticketId);
        if (ticket.getClientId() != getUserId().longValue()){
            throw new ServiceException(NO_TICKETS_FOUND);
        }
        return TicketInfoMapper.INSTANCE.mapTicketToTicketInfo(ticket);
    }

    public List<TicketInfo> getTicketsByEmployee(){
        var tickets = ticketService.getEmployeeManagedTickets(getUserId().longValue());
        return tickets.stream().map(TicketInfoMapper.INSTANCE::mapTicketToTicketInfo).toList();
    }

    public List<TicketInfo> getTicketsByDoctor(){
        var tickets = ticketService.getDoctorAssignedTickets(getUserId().longValue());
        return tickets.stream().map(TicketInfoMapper.INSTANCE::mapTicketToTicketInfo).toList();
    }

    public List<TicketInfo> getTicketsByDoctorWithStatus(TicketStatus status){
        var tickets = ticketService.getDoctorTicketsWithTicketStatus(getUserId().longValue(), status);
        return tickets.stream().map(TicketInfoMapper.INSTANCE::mapTicketToTicketInfo).toList();
    }

    public com.pweb.model.generated.PaymentInfo getPaymentById(Long paymentId){
        var payment = paymentService.getPaymentInfo(paymentId);
        if (payment.getClientId() != getUserId().longValue()){
            throw new ServiceException(NO_PAYMENT_INFO_FOUND);
        }
        return PaymentInfoMapper.INSTANCE.paymentInfoDBToPaymentInfoGen(payment);
    }
}