package com.pweb.MyClinic.mappers;

import com.pweb.MyClinic.model.db.PaymentInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentInfoMapper {

    PaymentInfoMapper INSTANCE = Mappers.getMapper(PaymentInfoMapper.class);

    PaymentInfo paymentInfoGenToPaymentInfoDB(com.pweb.model.generated.PaymentInfo source);

    com.pweb.model.generated.PaymentInfo paymentInfoDBToPaymentInfoGen(PaymentInfo source);
}