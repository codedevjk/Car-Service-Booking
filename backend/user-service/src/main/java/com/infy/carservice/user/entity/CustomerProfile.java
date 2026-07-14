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
    private String userId;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String contactNumber;
    
    private String address;
}
