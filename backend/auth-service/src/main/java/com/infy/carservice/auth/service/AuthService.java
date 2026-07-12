package com.infy.carservice.auth.service;
import com.infy.carservice.auth.dto.RegisterRequest;
import com.infy.carservice.auth.entity.UserCredentials;
import com.infy.carservice.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        UserCredentials user = new UserCredentials();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole("USER");
        userRepository.save(user);
        return "User added to the system";
    }
}
