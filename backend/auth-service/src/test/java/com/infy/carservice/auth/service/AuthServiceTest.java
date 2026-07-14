package com.infy.carservice.auth.service;

import com.infy.carservice.auth.dto.AuthRequest;
import com.infy.carservice.auth.dto.RegisterRequest;
import com.infy.carservice.auth.entity.UserCredentials;
import com.infy.carservice.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class AuthServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_Success() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("test@test.com");
        req.setPassword("pass");
        req.setFullName("Test User");

        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        
        UserCredentials saved = new UserCredentials();
        saved.setId(1L);
        saved.setUserId("U1");
        saved.setEmail("test@test.com");
        when(repository.save(any(UserCredentials.class))).thenReturn(saved);
        
        when(restTemplate.postForObject(anyString(), any(), eq(String.class))).thenReturn("Profile Created");

        assertDoesNotThrow(() -> authService.register(req));
        verify(repository, times(2)).save(any(UserCredentials.class));
    }

    @Test
    void testRegister_EmailExists() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("test@test.com");

        when(repository.findByEmail("test@test.com")).thenReturn(Optional.of(new UserCredentials()));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.register(req));
        assertEquals("Email already exists", ex.getMessage());
    }
}
