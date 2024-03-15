package com.pweb.MyClinic.service.workflow;

import com.pweb.MyClinic.security.model.User;
import com.pweb.MyClinic.security.service.JwtService;
import com.pweb.MyClinic.security.service.UserService;
import com.pweb.MyClinic.service.exception.ServiceException;
import com.pweb.model.generated.AuthResponse;
import com.pweb.model.generated.LoginRequest;
import com.pweb.model.generated.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static com.pweb.MyClinic.security.model.Role.USER;
import static com.pweb.MyClinic.service.error.ServiceError.LOGIN_FAILED;
import static com.pweb.MyClinic.service.error.ServiceError.REGISTER_FAILED;

@Service
@RequiredArgsConstructor
public class WorkflowService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthResponse login(LoginRequest request) {
        try {
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            if (authentication.isAuthenticated()) {
                var response = new AuthResponse();
                response.setAccessToken(jwtService.generateToken(request.getEmail()));
                response.setTokenType("Bearer");
                return response;
            }
        }catch (BadCredentialsException e){
            throw new ServiceException(LOGIN_FAILED);
        }
        return null;
    }

    public AuthResponse registerUser(RegisterRequest request){
        var user =  new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(USER);

        try {
            userService.addUser(user);
        } catch (Exception e){
            throw new ServiceException(REGISTER_FAILED);
        }

        var loginRequest = new LoginRequest().email(request.getEmail()).password(request.getPassword());
        return login(loginRequest);
    }
}