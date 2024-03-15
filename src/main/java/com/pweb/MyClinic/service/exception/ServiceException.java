package com.pweb.MyClinic.service.exception;

import com.pweb.MyClinic.service.error.ServiceError;
import lombok.Getter;

import static io.micrometer.common.util.StringUtils.isNotEmpty;

@Getter
public class ServiceException extends RuntimeException {

    private final ServiceError serviceError;

    public ServiceException(ServiceError serviceError) {
        super(serviceError.getMessage());
        this.serviceError = serviceError;
    }

    @Override
    public String getMessage() {
        if (isNotEmpty(super.getMessage())) {
            return super.getMessage();
        } else {
            return serviceError.getMessage();
        }
    }
}