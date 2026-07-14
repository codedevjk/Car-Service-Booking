package com.infy.carservice.booking.repository;
import com.infy.carservice.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    boolean existsByVehicleIdAndAppointmentDateAndTimeSlot(Long vehicleId, LocalDate appointmentDate, String timeSlot);
    List<Booking> findByCustomerId(String customerId);
    long countByCustomerId(String customerId);
    long countByCustomerIdAndStatusIn(String customerId, List<com.infy.carservice.booking.entity.BookingStatus> statuses);
    long countByCustomerIdAndStatus(String customerId, com.infy.carservice.booking.entity.BookingStatus status);
    long countByServiceIdAndStatusIn(Long serviceId, List<com.infy.carservice.booking.entity.BookingStatus> statuses);
}
