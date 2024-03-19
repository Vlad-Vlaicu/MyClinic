package com.pweb.MyClinic.mappers;

import com.pweb.model.generated.AddEmployeeRequest;
import com.pweb.model.generated.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeToRegisterReqMapper {

    EmployeeToRegisterReqMapper INSTANCE = Mappers.getMapper(EmployeeToRegisterReqMapper.class);

    RegisterRequest mapEmployeeToRegisterReqMapper(AddEmployeeRequest source);
}