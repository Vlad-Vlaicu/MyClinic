package com.pweb.MyClinic.service.company;

import com.pweb.MyClinic.model.db.PaymentInfo;
import com.pweb.MyClinic.repository.PaymentInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentInfoRepository repository;

    public PaymentInfo savePayment(PaymentInfo paymentInfo) {
        return repository.save(paymentInfo);
    }

    public PaymentInfo getPaymentInfo(Long id) {
        return repository.getPaymentInfoById(id);
    }
}