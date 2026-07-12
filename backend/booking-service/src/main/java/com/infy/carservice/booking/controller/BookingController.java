package com.infy.carservice.booking.controller;
import com.infy.carservice.booking.dto.BookingDTO;
import com.infy.carservice.booking.dto.DashboardSummaryDTO;
import com.infy.carservice.booking.entity.BookingStatus;
import com.infy.carservice.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    // TODO: Trainee to map and implement these endpoints (US07-US10)
    // 1. Map @PostMapping to create a booking (US07).
    @PostMapping
    public BookingDTO createBooking(@Valid @RequestBody BookingDTO dto) {
        return bookingService.createBooking(dto);
    }
    
    // 2. Map @PatchMapping to update booking status (US08).
    @PatchMapping("/{id}/status")
    public BookingDTO updateStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        return bookingService.updateBookingStatus(id, status);
    }
    
    // 3. Map @GetMapping to retrieve a customer's bookings (US08).
    @GetMapping("/customer/{customerId}")
    public List<BookingDTO> getCustomerBookings(@PathVariable Long customerId) {
        return bookingService.getCustomerBookings(customerId);
    }

    // 4. Map @GetMapping to retrieve the dashboard summary (US09).
    @GetMapping("/dashboard")
    public DashboardSummaryDTO getDashboard(@RequestParam(required = false) Long customerId, @RequestParam boolean isAdmin) {
        return bookingService.getDashboardSummary(customerId, isAdmin);
    }
    
    // 5. Map @GetMapping to search bookings by reference number (US10).
    @GetMapping("/search")
    public List<BookingDTO> searchBookings(@RequestParam String referenceNumber) {
        return bookingService.searchBookings(referenceNumber);
    }
}
