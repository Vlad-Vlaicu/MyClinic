package com.pweb.MyClinic.repository;

import com.pweb.MyClinic.model.db.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> getTicketsByClientId(Long clientId);
}