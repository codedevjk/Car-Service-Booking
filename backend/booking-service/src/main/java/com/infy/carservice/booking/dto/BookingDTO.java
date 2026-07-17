package com.infy.carservice.booking.dto;
import com.infy.carservice.booking.entity.BookingStatus;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class BookingDTO {
    private Long id;
    private String referenceNumber;
    private String customerId; // Handled by controller using X-User-Id
    @NotNull(message = "Please provide a valid vehicleId")
    private Long vehicleId;
    @NotNull(message = "Please provide a valid serviceId")
    private Long serviceId;
    @NotNull(message = "Please provide a valid appointmentDate")
    @FutureOrPresent(message = "Please provide a valid appointmentDate")
    private LocalDate appointmentDate;
    @NotBlank(message = "Please provide a valid timeSlot")
    private String timeSlot;
    private String problemDescription;
    
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

	public BookingDTO() {
		super();
		 
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
