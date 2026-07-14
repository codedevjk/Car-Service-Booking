package com.infy.carservice.user.controller;
import com.infy.carservice.user.dto.CustomerProfileDTO;
import com.infy.carservice.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/profile/{userId}")
    public CustomerProfileDTO getProfile(@PathVariable String userId, @RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        return userService.getProfile(userId, callerId, userRole);
    }
    
    @PutMapping("/profile/{userId}")
    public CustomerProfileDTO updateProfile(@PathVariable String userId, @Valid @RequestBody CustomerProfileDTO dto, @RequestHeader("X-User-Id") String callerId) {
        return userService.updateProfile(userId, dto, callerId);
    }
    
    @PostMapping("/profile")
    public String createProfile(@Valid @RequestBody CustomerProfileDTO dto) {
        return userService.createProfile(dto);
    }
    
    @GetMapping("/search")
    public java.util.List<String> searchUsersByName(@RequestParam String name, @RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        if (!"ADMIN".equals(userRole)) throw new RuntimeException("Unauthorized");
        return userService.searchUsersByName(name);
    }
    
    @GetMapping("/count")
    public Long countUsers(@RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        if (!"ADMIN".equals(userRole)) throw new RuntimeException("Unauthorized");
        return userService.getUserCount();
    }
}
