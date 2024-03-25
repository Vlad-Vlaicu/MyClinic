package com.pweb.MyClinic.service.company;

import com.pweb.MyClinic.model.db.PaymentInfo;
import com.pweb.MyClinic.repository.PaymentInfoRepository;
import com.pweb.MyClinic.service.error.ServiceError;
import com.pweb.MyClinic.service.exception.ServiceException;
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
        var payment =  repository.getPaymentInfoById(id);
        if (payment.isEmpty()){
            throw new ServiceException(ServiceError.NO_PAYMENT_INFO_FOUND);
        }
        return payment.get();
    }
}