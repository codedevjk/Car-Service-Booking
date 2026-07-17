package com.infy.carservice.booking.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String referenceNumber;
    @Column(nullable = false)
    private String customerId;
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
    
	public Booking(Long id, String referenceNumber, String customerId, Long vehicleId, Long serviceId,
			LocalDate appointmentDate, String timeSlot, String problemDescription, BookingStatus status) {
		super();
		this.id = id;
		this.referenceNumber = referenceNumber;
		this.customerId = customerId;
		this.vehicleId = vehicleId;
		this.serviceId = serviceId;
		this.appointmentDate = appointmentDate;
		this.timeSlot = timeSlot;
		this.problemDescription = problemDescription;
		this.status = status;
	}
	public Booking() {
		super();
		this.id = id;
		this.referenceNumber = referenceNumber;
		this.customerId = customerId;
		this.vehicleId = vehicleId;
		this.serviceId = serviceId;
		this.appointmentDate = appointmentDate;
		this.timeSlot = timeSlot;
		this.problemDescription = problemDescription;
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	public String getProblemDescription() {
		return problemDescription;
	}
	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}
	public BookingStatus getStatus() {
		return status;
	}
	public void setStatus(BookingStatus status) {
		this.status = status;
	}
    
}
