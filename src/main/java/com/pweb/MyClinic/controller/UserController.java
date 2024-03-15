package com.pweb.MyClinic.controller;

import com.pweb.MyClinic.service.workflow.WorkflowService;
import com.pweb.controller.generated.MyClinicApi;
import com.pweb.model.generated.AuthResponse;
import com.pweb.model.generated.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class UserController implements MyClinicApi {

    private final WorkflowService workflowService;

    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        var response = workflowService.login(loginRequest);
        return ok(response);
    }
}