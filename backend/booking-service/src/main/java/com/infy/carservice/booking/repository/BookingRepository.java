package com.infy.carservice.booking.repository;
import com.infy.carservice.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
