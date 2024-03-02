package com.pweb.MyClinic.security.service;

import com.pweb.MyClinic.security.model.User;
import com.pweb.MyClinic.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.pweb.MyClinic.security.config.SecurityConfig.encode;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = repository.findByEmail(username);

        // Converting userDetail to UserDetails
        return userDetail
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(User userInfo) {
        userInfo.setPassword(encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }
}
