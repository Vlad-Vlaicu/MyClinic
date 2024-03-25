package com.pweb.MyClinic.repository;

import com.pweb.MyClinic.model.db.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {

    Optional<PaymentInfo> getPaymentInfoById(Long id);
}