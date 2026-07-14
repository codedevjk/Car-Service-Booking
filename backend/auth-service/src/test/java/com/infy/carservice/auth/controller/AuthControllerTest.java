package com.infy.carservice.auth.controller;

import com.infy.carservice.auth.dto.AuthRequest;
import com.infy.carservice.auth.dto.RegisterRequest;
import com.infy.carservice.auth.entity.UserCredentials;
import com.infy.carservice.auth.repository.UserRepository;
import com.infy.carservice.auth.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("test@test.com");
        
        when(authService.register(any(RegisterRequest.class))).thenReturn("Success");

        ResponseEntity<Map<String, String>> response = authController.register(req);
        assertEquals(201, response.getStatusCode().value());
        assertEquals("Success", response.getBody().get("message"));
    }

    @Test
    void testLogin_Success() {
        AuthRequest req = new AuthRequest();
        req.setEmail("test@test.com");
        req.setPassword("pass");

        UserCredentials user = new UserCredentials();
        user.setEmail("test@test.com");
        user.setPassword("pass");
        user.setRole("Customer");
        user.setUserId("U1");
        user.setFullName("Test User");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        ResponseEntity<Map<String, String>> response = authController.login(req);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Customer", response.getBody().get("role"));
    }

    @Test
    void testLogin_Invalid() {
        AuthRequest req = new AuthRequest();
        req.setEmail("test@test.com");
        req.setPassword("wrong");

        UserCredentials user = new UserCredentials();
        user.setEmail("test@test.com");
        user.setPassword("pass");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> authController.login(req));
    }
}
