package com.pweb.MyClinic.mappers;

import com.pweb.MyClinic.model.db.ClientInfo;
import com.pweb.model.generated.AccountInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountInfoMapper {
    AccountInfoMapper INSTANCE = Mappers.getMapper(AccountInfoMapper.class);

    @Mapping(source = "personalData.name", target = "name")
    @Mapping(source = "personalData.cnp", target = "cnp")
    @Mapping(source = "personalData.phoneNumber", target = "phoneNumber")
    @Mapping(source = "personalData.birthday", target = "birthday")
    @Mapping(source = "personalData.email", target = "email")
    AccountInfo mapClientInfoToAccountInfo(ClientInfo source);
}