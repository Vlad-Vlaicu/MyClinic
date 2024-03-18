package com.pweb.MyClinic.helper;

import com.pweb.MyClinic.model.PersonalData;
import com.pweb.MyClinic.model.User;
import com.pweb.MyClinic.model.db.ClientInfo;
import com.pweb.MyClinic.model.db.PaymentInfo;
import com.pweb.MyClinic.model.db.Product;
import com.pweb.MyClinic.model.db.Ticket;
import com.pweb.model.generated.CreateTicketRequest;
import com.pweb.model.generated.RegisterRequest;

import static com.pweb.MyClinic.helper.TimeHelper.TIME_FORMATTER;
import static com.pweb.MyClinic.model.PaymentStatus.UNPAID;
import static com.pweb.MyClinic.model.Role.USER;
import static com.pweb.MyClinic.model.TicketStatus.CREATED;
import static java.math.RoundingMode.HALF_UP;
import static java.time.LocalDateTime.now;

public class UserHelper {

    public static User generateUser(RegisterRequest request) {
        var user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(USER);
        return user;
    }

    public static ClientInfo generateClientInfo(Integer userId, RegisterRequest request) {
        var clientInfo = new ClientInfo();
        clientInfo.setCreationDate(now().format(TIME_FORMATTER));
        clientInfo.setUserId(userId);
        clientInfo.setPersonalData(generatePersonalData(request));
        return clientInfo;
    }

    private static PersonalData generatePersonalData(RegisterRequest request) {
        var personalData = new PersonalData();
        personalData.setName(request.getName());
        personalData.setEmail(request.getEmail());
        personalData.setPhoneNumber(request.getPhoneNumber());
        personalData.setCnp(request.getCnp());
        personalData.setBirthday(request.getBirthday());
        return personalData;
    }

    public static Ticket generateTicket(CreateTicketRequest request) {
        var ticket = new Ticket();
        ticket.setCreationDate(now().format(TIME_FORMATTER));
        ticket.setProductId(request.getProductId().setScale(0, HALF_UP).longValue());
        ticket.setReservedTime(request.getPeriod());
        ticket.setTicketStatus(CREATED);
        return ticket;
    }

    public static PaymentInfo generatePaymentInfo(CreateTicketRequest request, Product product) {
        var paymentInfo = new PaymentInfo();
        paymentInfo.setStatus(UNPAID);
        paymentInfo.setProduct(product);
        paymentInfo.setPaymentType(request.getPaymentType().toString());
        paymentInfo.setCreationDate(now().format(TIME_FORMATTER));
        return paymentInfo;
    }
}