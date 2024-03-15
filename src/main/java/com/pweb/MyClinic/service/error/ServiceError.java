package com.pweb.MyClinic.service.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceError {

    LOGIN_FAILED("Username or password are incorrect"),
    REGISTER_FAILED( "Registering a new user has failed");
    private final String message;
}