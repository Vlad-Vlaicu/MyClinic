package com.pweb.MyClinic.mappers;

import com.pweb.MyClinic.model.db.PaymentInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentInfoMapper {

    PaymentInfoMapper INSTANCE = Mappers.getMapper(PaymentInfoMapper.class);

    @Mapping(source = "executionTime", target = "executionDate")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "status", target = "paymentStatus")
    com.pweb.model.generated.PaymentInfo paymentInfoDBToPaymentInfoGen(PaymentInfo source);
}