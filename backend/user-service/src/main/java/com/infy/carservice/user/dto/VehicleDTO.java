package com.infy.carservice.user.dto;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
@Data
public class VehicleDTO {
    private Long id;
    private Long customerId;
    @NotBlank
    private String registrationNumber;
    @NotBlank
    private String manufacturer;
    @NotBlank
    private String model;
    private String fuelType;
    private Integer manufacturingYear;
    private String color;
}
