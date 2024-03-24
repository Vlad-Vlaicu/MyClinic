package com.pweb.MyClinic.mappers;

import com.pweb.MyClinic.model.db.Ticket;
import com.pweb.model.generated.TicketInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketInfoMapper {

    TicketInfoMapper INSTANCE = Mappers.getMapper(TicketInfoMapper.class);

    @Mapping(source = "reservedTime", target = "period")
    TicketInfo mapTicketToTicketInfo(Ticket source);

    @Mapping(source = "period", target = "reservedTime")
    Ticket mapTicketInfoToTicket(TicketInfo source);
}