package com.pweb.MyClinic.repository;

import com.pweb.MyClinic.model.db.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {

    PaymentInfo getPaymentInfoById(Long id);
}