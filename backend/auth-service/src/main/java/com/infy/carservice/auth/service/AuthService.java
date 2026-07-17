package com.infy.carservice.auth.service;
import com.infy.carservice.auth.dto.RegisterRequest;
import com.infy.carservice.auth.entity.UserCredentials;
import com.infy.carservice.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.Map;
import java.util.HashMap;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        UserCredentials user = new UserCredentials();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole("CUSTOMER");
        user = userRepository.save(user);
        
        user.setUserId("U" + (user.getId() - 1));
        userRepository.save(user);
        
        try {
            createProfileInUserService(user);
        } catch (Exception e) {
            System.err.println("Fallback triggered: Could not create profile for " + user.getUserId() + ". Reason: " + e.getMessage());
        }

        return "User registered successfully";
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "createProfileFallback")
    public void createProfileInUserService(UserCredentials user) {
        Map<String, String> profileDto = new HashMap<>();
        profileDto.put("userId", user.getUserId());
        profileDto.put("fullName", user.getFullName());
        profileDto.put("email", user.getEmail());
        restTemplate.postForObject("http://user-service/api/users/profile", profileDto, String.class);
    }

    public void createProfileFallback(UserCredentials user, Throwable t) {
        System.err.println("Fallback triggered: Could not create profile for " + user.getUserId() + ". Reason: " + t.getMessage());
    }
}