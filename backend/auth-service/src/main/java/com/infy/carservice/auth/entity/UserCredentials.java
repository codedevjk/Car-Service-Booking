package com.infy.carservice.auth.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class UserCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
}
