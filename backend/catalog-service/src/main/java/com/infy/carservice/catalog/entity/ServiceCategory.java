package com.infy.carservice.catalog.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class ServiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    private Boolean active;
}
