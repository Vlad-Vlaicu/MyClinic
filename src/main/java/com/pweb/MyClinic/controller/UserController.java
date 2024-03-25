package com.pweb.MyClinic.controller;

import com.pweb.MyClinic.service.workflow.WorkflowService;
import com.pweb.controller.generated.MyClinicApi;
import com.pweb.model.generated.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class UserController implements MyClinicApi {

    private final WorkflowService workflowService;

    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        var response = workflowService.login(loginRequest);
        return ok(response);
    }

    @Override
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<PaymentInfo> payTicket(BigDecimal ticketId) {
        return ok().body(workflowService.payTicket(ticketId.longValue()));
    }

    public ResponseEntity<AuthResponse> registerUser (@RequestBody RegisterRequest request){
        var response = workflowService.registerUser(request);
        return ok(response);
    }

    @Override
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<PaymentInfo> getPaymentWithId(BigDecimal paymentId) {
        return ok().body(workflowService.getPaymentById(paymentId.longValue()));
    }

    @Override
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<TicketInfo> getTicketWithId(BigDecimal ticketId) {
        return ok().body(workflowService.getUserTicketById(ticketId.longValue()));
    }

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<AccountInfo> getAccountData(){
        var response = workflowService.getAccountInfo();
        return ok(response);
    }

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> createTicket(CreateTicketRequest request){
        var response = workflowService.createTicket(request);
        return ok(response);
    }

    @Override
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<TicketInfo>> getTickets() {
        return ok().body(workflowService.getUserTickets());
    }
}