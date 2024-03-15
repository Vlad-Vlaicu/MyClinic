package com.pweb.MyClinic.service.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceError {

    LOGIN_FAILED("Username or password are incorrect");

    private final String message;
}