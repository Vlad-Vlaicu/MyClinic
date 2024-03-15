package com.pweb.MyClinic.service.workflow;

import com.pweb.MyClinic.security.service.JwtService;
import com.pweb.MyClinic.service.exception.ServiceException;
import com.pweb.model.generated.AuthResponse;
import com.pweb.model.generated.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static com.pweb.MyClinic.service.error.ServiceError.LOGIN_FAILED;

@Service
@RequiredArgsConstructor
public class WorkflowService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest loginRequest) {
        try {
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                var response = new AuthResponse();
                response.setAccessToken(jwtService.generateToken(loginRequest.getEmail()));
                response.setTokenType("Bearer");
                return response;
            }
        }catch (BadCredentialsException e){
            throw new ServiceException(LOGIN_FAILED);
        }
        return null;
    }
}