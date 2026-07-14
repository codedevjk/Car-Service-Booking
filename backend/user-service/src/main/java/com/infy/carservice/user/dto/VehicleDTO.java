package com.infy.carservice.user.dto;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
@Data
public class VehicleDTO {
    private Long id;
    private String userId;
    
    @NotBlank(message = "Please provide a valid registrationNumber")
    @jakarta.validation.constraints.Pattern(regexp = "^[A-Z]{2}[A-Za-z0-9]{6}$", message = "Please provide a valid registrationNumber")
    private String registrationNumber;
    
    @NotBlank(message = "Please provide a valid manufacturer")
    private String manufacturer;
    
    @NotBlank(message = "Please provide a valid model")
    private String model;
    
    private String fuelType;
    
    private Integer manufacturingYear;
}
