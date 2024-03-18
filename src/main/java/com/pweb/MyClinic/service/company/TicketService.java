package com.pweb.MyClinic.service.company;

import com.pweb.MyClinic.model.db.Ticket;
import com.pweb.MyClinic.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repository;

    public Ticket saveTicket(Ticket ticket) {
        return repository.save(ticket);
    }

    public List<Ticket> getUserTickets(Long userId){
        return repository.getTicketsByClientId(userId);
    }
}