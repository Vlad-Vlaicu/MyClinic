package com.pweb.MyClinic.controller;

import com.pweb.MyClinic.service.workflow.WorkflowService;
import com.pweb.controller.generated.MyClinicFrontOfficeApi;
import com.pweb.model.generated.TicketInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class FrontOfficeController implements MyClinicFrontOfficeApi {

    private final WorkflowService workflowService;

    @PreAuthorize("hasAuthority('FRONT_OFFICE')")
    public ResponseEntity<String> hello(){
        return ok("Hello front office!");
    }

    @Override
    @PreAuthorize("hasAuthority('FRONT_OFFICE')")
    public ResponseEntity<Void> cancelProcessing(BigDecimal ticketId) {
        workflowService.cancelProcessing(ticketId.longValue());
        return ok().build();
    }

    @Override
    @PreAuthorize("hasAuthority('FRONT_OFFICE')")
    public ResponseEntity<Void> cancelTicket(BigDecimal ticketId) {
        workflowService.cancelTicket(ticketId.longValue());
        return ok().build();
    }

    @Override
    @PreAuthorize("hasAuthority('FRONT_OFFICE')")
    public ResponseEntity<TicketInfo> claimTicket() {
        return ok().body(workflowService.claimTicket());
    }

    @Override
    @PreAuthorize("hasAuthority('FRONT_OFFICE')")
    public ResponseEntity<Void> completeProcessing(BigDecimal ticketId, TicketInfo ticketInfo) {
        workflowService.completeProcessing(ticketId.longValue(), ticketInfo);
        return ok().build();
    }

    @Override
    @PreAuthorize("hasAuthority('FRONT_OFFICE')")
    public ResponseEntity<TicketInfo> getTicketById(BigDecimal ticketId) {
        return ok().body(workflowService.getTicketById(ticketId.longValue()));
    }

    @Override
    @PreAuthorize("hasAuthority('FRONT_OFFICE')")
    public ResponseEntity<List<TicketInfo>> getTickets() {
        return ok().body(workflowService.getTicketsByEmployee());
    }
}
