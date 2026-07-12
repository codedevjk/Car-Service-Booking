package com.infy.carservice.user.service;
import com.infy.carservice.user.dto.CustomerProfileDTO;
import com.infy.carservice.user.entity.CustomerProfile;
import com.infy.carservice.user.repository.CustomerProfileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    @Autowired
    private CustomerProfileRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    public CustomerProfileDTO getProfile(String email) {
        CustomerProfile profile = repository.findByEmail(email).orElseThrow(() -> new RuntimeException("Profile not found"));
        return modelMapper.map(profile, CustomerProfileDTO.class);
    }
    public CustomerProfileDTO updateProfile(String email, CustomerProfileDTO dto) {
        CustomerProfile profile = repository.findByEmail(email).orElse(new CustomerProfile());
        profile.setEmail(email);
        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());
        profile.setPhone(dto.getPhone());
        return modelMapper.map(repository.save(profile), CustomerProfileDTO.class);
    }
}
