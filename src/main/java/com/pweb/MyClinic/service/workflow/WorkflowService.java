package com.pweb.MyClinic.service.workflow;

import com.pweb.MyClinic.service.company.ClientInfoService;
import com.pweb.MyClinic.service.exception.ServiceException;
import com.pweb.MyClinic.service.security.JwtService;
import com.pweb.MyClinic.service.security.UserService;
import com.pweb.model.generated.AuthResponse;
import com.pweb.model.generated.LoginRequest;
import com.pweb.model.generated.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.pweb.MyClinic.helper.UserHelper.generateClientInfo;
import static com.pweb.MyClinic.helper.UserHelper.generateUser;
import static com.pweb.MyClinic.service.error.ServiceError.LOGIN_FAILED;
import static com.pweb.MyClinic.service.error.ServiceError.REGISTER_FAILED;

@Service
@RequiredArgsConstructor
public class WorkflowService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final ClientInfoService clientInfoService;

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

    @Transactional(rollbackFor = Exception.class)
    public AuthResponse registerUser(RegisterRequest request){
        var userInfo = generateUser(request);

        try {
            var savedUser = userService.addUser(userInfo);
            var clientInfo = generateClientInfo(savedUser.getId(), request);
            clientInfoService.saveClientInfo(clientInfo);

        } catch (Exception e){
            throw new ServiceException(REGISTER_FAILED);
        }

        var loginRequest = new LoginRequest().email(request.getEmail()).password(request.getPassword());
        return login(loginRequest);
    }
}