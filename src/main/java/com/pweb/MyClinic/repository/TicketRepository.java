package com.pweb.MyClinic.repository;

import com.pweb.MyClinic.model.TicketStatus;
import com.pweb.MyClinic.model.db.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> getTicketsByClientId(Long clientId);
    List<Ticket> getTicketsByTicketStatusAndClientId(TicketStatus status, Long clientId);
    Optional<Ticket> findFirstByTicketStatusOrderByCreationDate(TicketStatus status);
    Optional<Ticket> getTicketsById(Long id);
    List<Ticket> getTicketsByEmployeeId(Long employeeId);
    List<Ticket> getTicketsByDoctorIdAndTicketStatus(Long doctorId, TicketStatus status);
    List<Ticket> getTicketsByDoctorId(Long doctorId);
}