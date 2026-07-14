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

    @PostMapping
    public BookingDTO createBooking(@Valid @RequestBody BookingDTO dto, @RequestHeader("X-User-Id") String callerId) {
        dto.setCustomerId(callerId);
        return bookingService.createBooking(dto);
    }
    
    @PatchMapping("/{id}/status")
    public BookingDTO updateStatus(@PathVariable Long id, @RequestParam BookingStatus status, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        if (!"ADMIN".equals(userRole)) throw new RuntimeException("Unauthorized");
        return bookingService.updateBookingStatus(id, status);
    }

    @PatchMapping("/{id}/cancel")
    public BookingDTO cancelBooking(@PathVariable Long id, @RequestHeader("X-User-Id") String callerId) {
        return bookingService.cancelBooking(id, callerId);
    }
    
    @GetMapping("/customer/{customerId}")
    public List<BookingDTO> getCustomerBookings(@PathVariable String customerId) {
        return bookingService.getCustomerBookings(customerId);
    }

    @GetMapping
    public org.springframework.data.domain.Page<BookingDTO> getAllBookings(
            @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole,
            @org.springframework.data.web.PageableDefault(size = 20) org.springframework.data.domain.Pageable pageable) {
        if (!"ADMIN".equals(userRole)) throw new RuntimeException("Unauthorized");
        return bookingService.getAllBookings(pageable);
    }

    @GetMapping("/dashboard")
    public DashboardSummaryDTO getDashboard(@RequestParam(required = false) String customerId, @RequestParam boolean isAdmin) {
        return bookingService.getDashboardSummary(customerId, isAdmin);
    }
    
    @GetMapping("/search")
    public org.springframework.data.domain.Page<BookingDTO> searchBookings(
            @RequestParam(required = false) String referenceNumber,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) BookingStatus status,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate appointmentDate,
            @RequestHeader("X-User-Id") String callerId,
            @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole,
            @org.springframework.data.web.PageableDefault(size = 20) org.springframework.data.domain.Pageable pageable) {
        return bookingService.searchBookings(referenceNumber, customerName, status, appointmentDate, callerId, userRole, pageable);
    }
    
    @GetMapping("/statistics")
    public java.util.Map<String, Long> getBookingStatistics(@RequestHeader("X-User-Id") String callerId, @RequestHeader(value = "X-User-Role", defaultValue = "CUSTOMER") String userRole) {
        return bookingService.getBookingStatistics(callerId, userRole);
    }

    @GetMapping("/active/count")
    public Long getActiveBookingsCount(@RequestParam Long serviceId) {
        return bookingService.getActiveBookingsCountForService(serviceId);
    }
}
