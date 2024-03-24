package com.pweb.MyClinic.service.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceError {

    LOGIN_FAILED("Username or password are incorrect"),
    REGISTER_FAILED( "Registering a new user has failed"),
    TICKET_CREATION_FAILED("The creation of ticket failed"),
    CREATE_EMPLOYEE_FAILED("The creation of a new employee failed"),
    NO_TICKETS_FOUND("No tickets found"),
    TICKET_IDS_DONT_MATCH("Ticket's ids don't match"),
    DOCTOR_SELECTED_IS_NOT_MEDIC("Doctor selected is not a medic user");

    private final String message;
}