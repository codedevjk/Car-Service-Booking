package com.infy.carservice.catalog.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
@Entity
@Data
public class CarService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long categoryId;
    @Column(nullable = false)
    private String name;
    private String description;
    private Integer estimatedDuration;
    private BigDecimal price;
    private Boolean availabilityStatus;
}
