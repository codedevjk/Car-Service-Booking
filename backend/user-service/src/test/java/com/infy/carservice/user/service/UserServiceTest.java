package com.infy.carservice.user.service;

import com.infy.carservice.user.dto.CustomerProfileDTO;
import com.infy.carservice.user.entity.CustomerProfile;
import com.infy.carservice.user.repository.CustomerProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private CustomerProfileRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProfile_Success() {
        CustomerProfile cp = new CustomerProfile();
        cp.setUserId("U1");
        cp.setEmail("test@test.com");
        
        CustomerProfileDTO dto = new CustomerProfileDTO();
        dto.setUserId("U1");
        dto.setEmail("test@test.com");

        when(repository.findByUserId("U1")).thenReturn(Optional.of(cp));
        when(modelMapper.map(cp, CustomerProfileDTO.class)).thenReturn(dto);

        CustomerProfileDTO result = userService.getProfile("U1", "U1", "CUSTOMER");
        assertNotNull(result);
        assertEquals("U1", result.getUserId());
    }

    @Test
    void testGetProfile_NotFound() {
        when(repository.findByUserId("U1")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.getProfile("U1", "U1", "CUSTOMER"));
        assertEquals("Profile not found", ex.getMessage());
    }

    @Test
    void testUpdateProfile_Success() {
        CustomerProfileDTO dto = new CustomerProfileDTO();
        dto.setFullName("Updated Name");
        dto.setContactNumber("9876543210");
        dto.setAddress("New Address");

        CustomerProfile existing = new CustomerProfile();
        existing.setUserId("U1");

        when(repository.findByUserId("U1")).thenReturn(Optional.of(existing));
        when(repository.save(any(CustomerProfile.class))).thenReturn(existing);
        when(modelMapper.map(existing, CustomerProfileDTO.class)).thenReturn(dto);

        CustomerProfileDTO result = userService.updateProfile("U1", dto, "U1");
        assertEquals("Updated Name", result.getFullName());
    }
}
