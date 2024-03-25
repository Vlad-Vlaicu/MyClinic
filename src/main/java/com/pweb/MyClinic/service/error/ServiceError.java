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
    DOCTOR_SELECTED_IS_NOT_MEDIC("Doctor selected is not a medic user"),
    INVALID_TICKET_STATUS("Invalid ticket status"),
    TICKET_IS_ALREADY_BEING_PROCESSED("Ticket is already being processed"),
    TICKET_IS_ALREADY_DONE("Ticket was already resolved"),
    TICKET_IS_NOT_ELIGIBLE_TO_BE_RESOLVED("Can't resolve ticket as it is not in progress"),
    TICKET_PAYMENT_ERROR_WRONG_PAYMENT_TYPE("Payment could not be processed"),
    NO_PAYMENT_INFO_FOUND("No payment info found");

    private final String message;
}