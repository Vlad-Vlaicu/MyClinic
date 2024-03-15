package com.pweb.MyClinic.repository;

import com.pweb.MyClinic.model.db.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}