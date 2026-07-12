package com.infy.carservice.user.dto;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
@Data
public class CustomerProfileDTO {
    private Long id;
    @NotBlank
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String phone;
}
