package com.infy.carservice.user.service;
import com.infy.carservice.user.dto.CustomerProfileDTO;
import com.infy.carservice.user.entity.CustomerProfile;
import com.infy.carservice.user.repository.CustomerProfileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
@Service
public class UserService {
    @Autowired
    private CustomerProfileRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    public CustomerProfileDTO getProfile(String userId, String callerId, String userRole) {
        if (!"ADMIN".equals(userRole) && !userId.equals(callerId)) {
            throw new RuntimeException("Unauthorized");
        }
        CustomerProfile profile = repository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Profile not found"));
        return modelMapper.map(profile, CustomerProfileDTO.class);
    }
    
    public CustomerProfileDTO updateProfile(String userId, CustomerProfileDTO dto, String callerId) {
        if (!userId.equals(callerId)) {
            throw new RuntimeException("Unauthorized update: Only the owning customer can update the profile");
        }
        CustomerProfile profile = repository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.setFullName(dto.getFullName());
        profile.setContactNumber(dto.getContactNumber());
        profile.setAddress(dto.getAddress());
        return modelMapper.map(repository.save(profile), CustomerProfileDTO.class);
    }
    
    public String createProfile(CustomerProfileDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Profile with email already exists");
        }
        CustomerProfile profile = new CustomerProfile();
        profile.setUserId(dto.getUserId());
        profile.setFullName(dto.getFullName());
        profile.setEmail(dto.getEmail());
        repository.save(profile);
        return "Profile created successfully";
    }

    public long getUserCount() {
        return repository.count();
    }

    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "default", fallbackMethod = "searchUsersByNameFallback")
    public List<String> searchUsersByName(String name) {
        return repository.findByFullNameContainingIgnoreCase(name)
                .stream()
                .map(CustomerProfile::getUserId)
                .collect(Collectors.toList());
    }

    public List<String> searchUsersByNameFallback(String name, Throwable t) {
        return new ArrayList<>();
    }
}
