package com.pweb.MyClinic.controller;

import com.pweb.MyClinic.model.TicketStatus;
import com.pweb.MyClinic.service.workflow.WorkflowService;
import com.pweb.controller.generated.MyClinicMedicApi;
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
public class MedicController implements MyClinicMedicApi {

    private final WorkflowService workflowService;

    @Override
    @PreAuthorize("hasAuthority('MEDIC')")
    public ResponseEntity<Void> cancelTicket(BigDecimal ticketId) {
        workflowService.cancelTicket(ticketId.longValue());
        return ok().build();
    }

    @Override
    @PreAuthorize("hasAuthority('MEDIC')")
    public ResponseEntity<Void> finishTicket(BigDecimal ticketId) {
        workflowService.finishTicket(ticketId.longValue());
        return ok().build();
    }

    @Override
    @PreAuthorize("hasAuthority('MEDIC')")
    public ResponseEntity<List<TicketInfo>> getCurrentTickets() {
        return ok().body(workflowService.getTicketsByDoctorWithStatus(TicketStatus.IN_PROGRESS));
    }

    @Override
    @PreAuthorize("hasAuthority('MEDIC')")
    public ResponseEntity<TicketInfo> getTicketById(BigDecimal ticketId) {
        return ok().body(workflowService.getTicketById(ticketId.longValue()));
    }

    @Override
    @PreAuthorize("hasAuthority('MEDIC')")
    public ResponseEntity<List<TicketInfo>> getTickets() {
        return ok().body(workflowService.getTicketsByDoctor());
    }

    @Override
    @PreAuthorize("hasAuthority('MEDIC')")
    public ResponseEntity<String> hello() {
        return ok("Hello medic!");
    }
}