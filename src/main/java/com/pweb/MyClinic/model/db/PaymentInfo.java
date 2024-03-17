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
@Entity(name = "payments")
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private Long clientId;
    private String executionTime;

    @JdbcTypeCode(JSON)
    private ProductInfo product;
    private String paymentType;
    private String additionalData;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}