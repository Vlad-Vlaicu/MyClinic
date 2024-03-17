package com.pweb.MyClinic.model.db;

import com.pweb.MyClinic.model.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private Long clientId;
    private Long employeeId;
    private Long productId;
    private String creationDate;
    private String reserved_time;
    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}