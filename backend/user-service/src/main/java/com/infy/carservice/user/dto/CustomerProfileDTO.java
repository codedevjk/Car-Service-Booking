package com.infy.carservice.user.dto;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
@Data
public class CustomerProfileDTO {
    private String userId;
    
    @NotBlank(message = "Please provide a valid fullName")
    private String fullName;

    @NotBlank(message = "Please provide a valid email")
    @jakarta.validation.constraints.Email(message = "Please provide a valid email")
    private String email;

    private String contactNumber;
    
    private String address;
}
