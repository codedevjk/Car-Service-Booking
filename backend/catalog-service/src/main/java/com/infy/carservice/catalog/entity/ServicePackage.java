package com.infy.carservice.catalog.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
@Entity
@Data
public class ServicePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long categoryId;
    @Column(nullable = false)
    private String name;
    private String description;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
}
