package com.infy.carservice.user.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long customerId;
    @Column(unique = true, nullable = false)
    private String registrationNumber;
    @Column(nullable = false)
    private String manufacturer;
    @Column(nullable = false)
    private String model;
    private String fuelType;
    private Integer manufacturingYear;
    private String color;
}
