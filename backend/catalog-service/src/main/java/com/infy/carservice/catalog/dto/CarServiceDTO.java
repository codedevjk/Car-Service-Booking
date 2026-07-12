package com.infy.carservice.catalog.dto;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
@Data
public class CarServiceDTO {
    private Long id;
    private Long categoryId;
    @NotBlank
    private String name;
    private String description;
    private Integer estimatedDuration;
    private BigDecimal price;
    private Boolean availabilityStatus;
}
