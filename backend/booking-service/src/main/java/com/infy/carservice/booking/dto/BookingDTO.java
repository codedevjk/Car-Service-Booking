package com.infy.carservice.booking.dto;
import com.infy.carservice.booking.entity.BookingStatus;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
@Data
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
    private BookingStatus status;
}
