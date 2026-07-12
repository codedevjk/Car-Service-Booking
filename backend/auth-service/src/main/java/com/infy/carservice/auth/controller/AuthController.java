package com.infy.carservice.auth.controller;
import com.infy.carservice.auth.dto.AuthRequest;
import com.infy.carservice.auth.dto.RegisterRequest;
import com.infy.carservice.auth.entity.UserCredentials;
import com.infy.carservice.auth.repository.UserRepository;
import com.infy.carservice.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }
    
    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody AuthRequest request) {
        UserCredentials user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(request.getPassword().equals(user.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            return response;
        }
        throw new RuntimeException("Invalid credentials");
    }
}
