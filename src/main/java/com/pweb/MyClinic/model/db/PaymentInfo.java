package com.pweb.MyClinic.model.db;

import com.pweb.MyClinic.model.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import static org.hibernate.type.SqlTypes.JSON;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "payment_info")
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "user_id")
    private Long clientId;

    @Column(name = "execution_time")
    private String executionTime;

    @JdbcTypeCode(JSON)
    @Column(name = "product")
    private Product product;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "additional_data")
    private String additionalData;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "creation_date")
    private String creationDate;
}