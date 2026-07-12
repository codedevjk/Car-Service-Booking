package com.infy.carservice.booking.dto;
import com.infy.carservice.booking.entity.BookingStatus;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
@Data
public class BookingDTO {
    private Long id;
    private String referenceNumber;
    @NotNull
    private Long customerId;
    @NotNull
    private Long vehicleId;
    @NotNull
    private Long serviceId;
    @NotNull
    private LocalDate appointmentDate;
    @NotNull
    private String timeSlot;
    private String problemDescription;
    private BookingStatus status;
}
