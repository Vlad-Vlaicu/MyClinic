package com.pweb.MyClinic.repository;

import com.pweb.MyClinic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> getUserById(Integer id);
}
