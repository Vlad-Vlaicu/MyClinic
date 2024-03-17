package com.pweb.MyClinic.service.security;

import com.pweb.MyClinic.model.User;
import com.pweb.MyClinic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.pweb.MyClinic.config.SecurityConfig.encode;

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

    public User addUser(User userInfo) {
        userInfo.setPassword(encode(userInfo.getPassword()));
        return repository.save(userInfo);
    }
}
