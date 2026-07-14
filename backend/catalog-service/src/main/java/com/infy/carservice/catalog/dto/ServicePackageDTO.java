package com.infy.carservice.catalog.dto;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
@Data
public class ServicePackageDTO {
    private Long id;
    private Long categoryId;
    @NotBlank(message = "Please provide a valid name")
    private String name;
    private String description;
    private BigDecimal price;
    private com.infy.carservice.catalog.entity.AvailabilityStatus availabilityStatus;
}
