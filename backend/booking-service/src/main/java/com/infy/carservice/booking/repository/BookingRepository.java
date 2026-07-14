package com.infy.carservice.booking.repository;
import com.infy.carservice.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.infy.carservice.booking.entity.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    boolean existsByVehicleIdAndAppointmentDateAndTimeSlot(Long vehicleId, LocalDate appointmentDate, String timeSlot);
    List<Booking> findByCustomerId(String customerId);
    long countByCustomerId(String customerId);
    long countByCustomerIdAndStatusIn(String customerId, List<BookingStatus> statuses);
    long countByCustomerIdAndStatus(String customerId, BookingStatus status);
    long countByServiceIdAndStatusIn(Long serviceId, List<BookingStatus> statuses);
}
