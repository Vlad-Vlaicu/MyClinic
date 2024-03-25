package com.pweb.MyClinic.service.company;

import com.pweb.MyClinic.model.TicketStatus;
import com.pweb.MyClinic.model.db.Ticket;
import com.pweb.MyClinic.repository.TicketRepository;
import com.pweb.MyClinic.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pweb.MyClinic.model.TicketStatus.*;
import static com.pweb.MyClinic.service.error.ServiceError.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repository;

    public Ticket saveTicket(Ticket ticket) {
        return repository.save(ticket);
    }

    public List<Ticket> getUserTickets(Long userId){
        return repository.getTicketsByClientId(userId);
    }

    @Transactional
    public Ticket claimTicket(Long employeeId){
        var ticket = repository.findFirstByTicketStatusOrderByCreationDate(CREATED);
        if (ticket.isEmpty()){
            log.info("No available tickets have been found");
            throw new ServiceException(NO_TICKETS_FOUND);
        }
        log.info("ticket with id {} was claimed by employee {}", ticket.get().getId(), employeeId);
        ticket.get().setTicketStatus(PROCESSING);
        ticket.get().setEmployeeId(employeeId);
        repository.save(ticket.get());
        log.info("Ticket with id {} was claimed", ticket.get().getId());
        return ticket.get();
    }

    public void updateTicket(Ticket ticket){
        repository.save(ticket);
    }

    @Transactional
    public void releaseTicket(Long ticketId, TicketStatus status){
        var ticket = repository.getTicketsById(ticketId);
        if (ticket.isEmpty()){
            log.info("Ticket with id {} was not found", ticketId);
            throw new ServiceException(NO_TICKETS_FOUND);
        }

        ticket.get().setTicketStatus(status);
        ticket.get().setEmployeeId(null);
        repository.save(ticket.get());
        log.info("Ticket with id {} was released", ticketId);
    }

    @Transactional
    public void finishTicket(Long ticketId){
        var ticket = repository.getTicketsById(ticketId);
        if (ticket.isEmpty()){
            log.info("Ticket with id {} was not found", ticketId);
            throw new ServiceException(NO_TICKETS_FOUND);
        }

        if (ticket.get().getTicketStatus() != IN_PROGRESS){
            log.info("ticket status expected to be in progress but was {}", ticket.get().getTicketStatus().toString());
            throw new ServiceException(TICKET_IS_NOT_ELIGIBLE_TO_BE_RESOLVED);
        }

        ticket.get().setTicketStatus(DONE);
        repository.save(ticket.get());
        log.info("Ticket with id {} was finished", ticketId);
    }

    @Transactional
    public void cancelTicket(Long ticketId, Long employeeId){
        var ticket = repository.getTicketsById(ticketId);
        if (ticket.isEmpty()){
            throw new ServiceException(NO_TICKETS_FOUND);
        }
        if (ticket.get().getTicketStatus() == DONE){
            throw new ServiceException(TICKET_IS_ALREADY_DONE);
        }
        ticket.get().setTicketStatus(CANCELLED);
        ticket.get().setEmployeeId(employeeId);
        repository.save(ticket.get());
        log.info("Ticket with id {} was cancelled", ticketId);
    }

    public Ticket getTicketById(Long ticketId){
        var ticket = repository.getTicketsById(ticketId);
        if (ticket.isEmpty()){
            throw new ServiceException(NO_TICKETS_FOUND);
        }
        return ticket.get();
    }

    public List<Ticket> getEmployeeManagedTickets(Long employeeId){
        return repository.getTicketsByEmployeeId(employeeId);
    }

    public List<Ticket> getDoctorAssignedTickets(Long doctorId){
        return repository.getTicketsByDoctorId(doctorId);
    }

    public List<Ticket> getDoctorTicketsWithTicketStatus(Long employeeId, TicketStatus status){
        return repository.getTicketsByDoctorIdAndTicketStatus(employeeId, status);
    }
}