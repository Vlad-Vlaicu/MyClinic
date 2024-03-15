package com.pweb.MyClinic.model.db;

import com.pweb.MyClinic.model.PaymentStatus;
import com.pweb.MyClinic.model.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.util.List;

import static org.hibernate.type.SqlTypes.JSON;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "payments")
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long clientId;
    private String executionTime;

    @JdbcTypeCode(JSON)
    private List<ProductInfo> products;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private String additionalData;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}