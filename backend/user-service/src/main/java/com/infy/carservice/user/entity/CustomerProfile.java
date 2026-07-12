package com.infy.carservice.user.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class CustomerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String phone;
}
