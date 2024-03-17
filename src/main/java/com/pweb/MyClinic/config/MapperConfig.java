package com.pweb.MyClinic.config;

import org.mapstruct.InjectionStrategy;

import java.time.OffsetDateTime;

@org.mapstruct.MapperConfig(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        imports = {OffsetDateTime.class}
)
public interface MapperConfig {
}