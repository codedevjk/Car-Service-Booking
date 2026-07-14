package com.infy.carservice.booking.service;

import com.infy.carservice.booking.dto.BookingDTO;
import com.infy.carservice.booking.entity.Booking;
import com.infy.carservice.booking.entity.BookingStatus;
import com.infy.carservice.booking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking_Success() {
        BookingDTO dto = new BookingDTO();
        dto.setVehicleId(1L);
        dto.setServiceId(1L);
        dto.setCustomerId("U1");
        dto.setAppointmentDate(LocalDate.now().plusDays(1));
        dto.setTimeSlot("10:00 AM - 11:00 AM");

        java.util.Map<String, Object> vehicleResp = new java.util.HashMap<>();
        vehicleResp.put("userId", "U1");
        when(restTemplate.getForObject("http://user-service/api/vehicles/1", java.util.Map.class)).thenReturn(vehicleResp);

        java.util.Map<String, Object> serviceResp = new java.util.HashMap<>();
        serviceResp.put("availabilityStatus", true);
        when(restTemplate.getForObject("http://catalog-service/api/service-packages/1", java.util.Map.class)).thenReturn(serviceResp);

        when(bookingRepository.existsByVehicleIdAndAppointmentDateAndTimeSlot(any(), any(), any())).thenReturn(false);

        Booking booking = new Booking();
        when(modelMapper.map(dto, Booking.class)).thenReturn(booking);
        
        Booking saved = new Booking();
        saved.setId(1L);
        when(bookingRepository.save(any(Booking.class))).thenReturn(saved);
        
        when(modelMapper.map(saved, BookingDTO.class)).thenReturn(dto);

        BookingDTO result = bookingService.createBooking(dto);
        assertNotNull(result);
    }

    @Test
    void testUpdateBookingStatus_Success() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStatus(BookingStatus.PENDING);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        
        BookingDTO dto = new BookingDTO();
        dto.setStatus(BookingStatus.CONFIRMED);
        when(modelMapper.map(booking, BookingDTO.class)).thenReturn(dto);

        BookingDTO result = bookingService.updateBookingStatus(1L, BookingStatus.CONFIRMED);
        assertEquals(BookingStatus.CONFIRMED, result.getStatus());
    }

    @Test
    void testSearchBookings_AsAdmin() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setReferenceNumber("B123");
        
        BookingDTO dto = new BookingDTO();
        dto.setReferenceNumber("B123");

        org.springframework.data.domain.Page<Booking> page = new org.springframework.data.domain.PageImpl<>(java.util.Collections.singletonList(booking));
        when(bookingRepository.findAll(any(org.springframework.data.jpa.domain.Specification.class), any(org.springframework.data.domain.Pageable.class))).thenReturn(page);
        when(modelMapper.map(booking, BookingDTO.class)).thenReturn(dto);

        org.springframework.data.domain.Page<BookingDTO> result = bookingService.searchBookings("B123", null, null, null, "A1", org.springframework.data.domain.PageRequest.of(0, 10));
        assertEquals(1, result.getContent().size());
        assertEquals("B123", result.getContent().get(0).getReferenceNumber());
    }

    @Test
    void testGetBookingStatistics_AsCustomer() {
        when(bookingRepository.countByCustomerIdAndStatus(eq("U1"), any(BookingStatus.class))).thenReturn(5L);
        
        java.util.Map<String, Long> stats = bookingService.getBookingStatistics("U1");
        assertNotNull(stats);
        assertEquals(5L, stats.get("PENDING"));
    }
}
