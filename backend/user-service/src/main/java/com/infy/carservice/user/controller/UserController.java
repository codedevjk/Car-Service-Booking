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
    @GetMapping("/profile")
    public CustomerProfileDTO getProfile(@RequestParam String email) {
        // Token validation was removed.
        return userService.getProfile(email);
    }
    @PutMapping("/profile")
    public CustomerProfileDTO updateProfile(@RequestParam String email, @Valid @RequestBody CustomerProfileDTO dto) {
        return userService.updateProfile(email, dto);
    }
}
