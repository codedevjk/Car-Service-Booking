package com.infy.carservice.booking.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String referenceNumber;
    @Column(nullable = false)
    private Long customerId;
    @Column(nullable = false)
    private Long vehicleId;
    @Column(nullable = false)
    private Long serviceId;
    @Column(nullable = false)
    private LocalDate appointmentDate;
    @Column(nullable = false)
    private String timeSlot;
    private String problemDescription;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;
}
