package com.pweb.MyClinic.controller;

import com.pweb.MyClinic.service.exception.ServiceException;
import com.pweb.MyClinic.service.workflow.WorkflowService;
import com.pweb.controller.generated.MyClinicAdminApi;
import com.pweb.model.generated.AddEmployeeRequest;
import io.jsonwebtoken.io.SerialException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import static com.pweb.MyClinic.service.error.ServiceError.CREATE_EMPLOYEE_FAILED;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class AdminController implements MyClinicAdminApi {

    private final WorkflowService workflowService;

    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> hello(){
        return ok("Hello admin!");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> addEmployee(AddEmployeeRequest request) {
        try {
            workflowService.addEmployee(request);
        } catch (Exception e) {
            throw new ServiceException(CREATE_EMPLOYEE_FAILED);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}