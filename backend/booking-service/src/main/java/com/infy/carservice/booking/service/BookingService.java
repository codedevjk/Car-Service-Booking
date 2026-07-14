package com.infy.carservice.booking.service;
import com.infy.carservice.booking.dto.BookingDTO;
import com.infy.carservice.booking.dto.DashboardSummaryDTO;
import com.infy.carservice.booking.entity.Booking;
import com.infy.carservice.booking.entity.BookingStatus;
import com.infy.carservice.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.time.LocalDate;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ModelMapper modelMapper;

    @CircuitBreaker(name = "default", fallbackMethod = "createBookingFallback")
    public BookingDTO createBooking(BookingDTO dto) {
        // 1. Fetch Vehicle to verify ownership
        try {
            // Ideally we get a VehicleDTO, but a generic Map is fine to extract the userId
            Map<String, Object> vehicle = restTemplate.getForObject("http://user-service/api/vehicles/" + dto.getVehicleId(), Map.class);
            if (vehicle == null || !dto.getCustomerId().equals(vehicle.get("userId"))) {
                throw new RuntimeException("Vehicle does not belong to the user");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Vehicle not found");
        }

        // 2. Fetch Service Package to verify it is ACTIVE
        try {
            Map<String, Object> servicePackage = restTemplate.getForObject("http://catalog-service/api/service-packages/" + dto.getServiceId(), Map.class);
            if (servicePackage == null) {
                throw new RuntimeException("Service Package not found");
            }
            Boolean isActive = (Boolean) servicePackage.get("availabilityStatus");
            if (isActive == null || !isActive) {
                throw new RuntimeException("Cannot book an inactive service");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Service Package not found");
        }

        // 3. Check for past date
        if (dto.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Appointment date cannot be in the past");
        }

        // 4. Check for duplicates
        if (bookingRepository.existsByVehicleIdAndAppointmentDateAndTimeSlot(dto.getVehicleId(), dto.getAppointmentDate(), dto.getTimeSlot())) {
            throw new RuntimeException("A booking for this vehicle already exists on the selected date and time slot");
        }

        Booking booking = modelMapper.map(dto, Booking.class);
        booking.setReferenceNumber("B" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        booking.setStatus(BookingStatus.PENDING);
        
        Booking saved = bookingRepository.save(booking);
        return modelMapper.map(saved, BookingDTO.class);
    }
    
    public BookingDTO createBookingFallback(BookingDTO dto, Throwable t) {
        throw new RuntimeException("Service temporarily unavailable. Please try booking later. Reason: " + t.getMessage());
    }
    
    public BookingDTO updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        
        BookingStatus current = booking.getStatus();
        boolean valid = false;

        if (current == BookingStatus.PENDING && (status == BookingStatus.CONFIRMED || status == BookingStatus.CANCELLED)) {
            valid = true;
        } else if (current == BookingStatus.CONFIRMED && status == BookingStatus.IN_SERVICE) {
            valid = true;
        } else if (current == BookingStatus.IN_SERVICE && status == BookingStatus.READY_FOR_DELIVERY) {
            valid = true;
        } else if (current == BookingStatus.READY_FOR_DELIVERY && status == BookingStatus.COMPLETED) {
            valid = true;
        }

        if (!valid) {
            throw new RuntimeException("Invalid booking status transition from " + current + " to " + status);
        }

        booking.setStatus(status);
        return modelMapper.map(bookingRepository.save(booking), BookingDTO.class);
    }

    public Page<BookingDTO> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable).map(b -> modelMapper.map(b, BookingDTO.class));
    }

    public BookingDTO cancelBooking(Long id, String customerId) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        if (!booking.getCustomerId().equals(customerId)) {
            throw new RuntimeException("Unauthorized");
        }
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Only PENDING bookings can be cancelled");
        }
        booking.setStatus(BookingStatus.CANCELLED);
        return modelMapper.map(bookingRepository.save(booking), BookingDTO.class);
    }
    
    public List<BookingDTO> getCustomerBookings(String customerId) {
        return bookingRepository.findByCustomerId(customerId).stream()
                .map(b -> modelMapper.map(b, BookingDTO.class))
                .toList();
    }
    
    @CircuitBreaker(name = "default", fallbackMethod = "searchBookingsFallback")
    public Page<BookingDTO> searchBookings(String referenceNumber, String customerName, BookingStatus status, LocalDate appointmentDate, String callerId, String userRole, Pageable pageable) {
        Specification<Booking> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (!"ADMIN".equals(userRole)) {
                predicates.add(cb.equal(root.get("customerId"), callerId));
            } else if (customerName != null && !customerName.isEmpty()) {
                HttpHeaders headers = new HttpHeaders();
                headers.set("X-User-Id", callerId);
                HttpEntity<String> entity = new HttpEntity<>(headers);
                try {
                    ResponseEntity<String[]> res = restTemplate.exchange(
                            "http://user-service/api/users/search?name=" + customerName,
                            HttpMethod.GET,
                            entity,
                            String[].class
                    );
                    String[] userIds = res.getBody();
                    if (userIds != null && userIds.length > 0) {
                        In<String> inClause = cb.in(root.get("customerId"));
                        for (String id : userIds) {
                            inClause.value(id);
                        }
                        predicates.add(inClause);
                    } else {
                        predicates.add(cb.equal(root.get("customerId"), "NO_MATCH"));
                    }
                } catch (Exception e) {
                    predicates.add(cb.equal(root.get("customerId"), "ERROR"));
                }
            }

            if (referenceNumber != null && !referenceNumber.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("referenceNumber")), "%" + referenceNumber.toLowerCase() + "%"));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (appointmentDate != null) {
                predicates.add(cb.equal(root.get("appointmentDate"), appointmentDate));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return bookingRepository.findAll(spec, pageable).map(b -> modelMapper.map(b, BookingDTO.class));
    }
    
    public Page<BookingDTO> searchBookingsFallback(String referenceNumber, String customerName, BookingStatus status, LocalDate appointmentDate, String callerId, String userRole, Pageable pageable, Throwable t) {
        throw new RuntimeException("Search is temporarily unavailable. Reason: " + t.getMessage());
    }
    
    public Map<String, Long> getBookingStatistics(String callerId, String userRole) {
        Map<String, Long> stats = new HashMap<>();
        for (BookingStatus status : BookingStatus.values()) {
            long count;
            if ("ADMIN".equals(userRole)) {
                count = bookingRepository.count((root, query, cb) -> cb.equal(root.get("status"), status));
            } else {
                count = bookingRepository.countByCustomerIdAndStatus(callerId, status);
            }
            stats.put(status.name(), count);
        }
        return stats;
    }
    
    @CircuitBreaker(name = "default", fallbackMethod = "getDashboardSummaryFallback")
    public DashboardSummaryDTO getDashboardSummary(String customerId, boolean isAdmin) {
        DashboardSummaryDTO summary = new DashboardSummaryDTO();
        
        HttpHeaders headers = new HttpHeaders();
        if (isAdmin) {
            headers.set("X-User-Id", "A1"); // Use an admin header for service-to-service calls
        } else {
            headers.set("X-User-Id", customerId);
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);

        if (isAdmin) {
            summary.setTotalBookings(bookingRepository.count());
            
            try {
                ResponseEntity<Long> cRes = restTemplate.exchange("http://user-service/api/users/count", HttpMethod.GET, entity, Long.class);
                summary.setTotalCustomers(cRes.getBody() != null ? cRes.getBody() : 0);
            } catch (Exception e) {}
            
            try {
                ResponseEntity<Long> catRes = restTemplate.exchange("http://catalog-service/api/categories/count", HttpMethod.GET, entity, Long.class);
                summary.setTotalCategories(catRes.getBody() != null ? catRes.getBody() : 0);
            } catch (Exception e) {}
            
            try {
                ResponseEntity<Long> sRes = restTemplate.exchange("http://catalog-service/api/service-packages/count", HttpMethod.GET, entity, Long.class);
                summary.setTotalServices(sRes.getBody() != null ? sRes.getBody() : 0);
            } catch (Exception e) {}

        } else {
            summary.setTotalBookings(bookingRepository.countByCustomerId(customerId));
            summary.setCompletedBookings(bookingRepository.countByCustomerIdAndStatus(customerId, BookingStatus.COMPLETED));
            summary.setActiveBookings(bookingRepository.countByCustomerIdAndStatusIn(customerId, Arrays.asList(
                    BookingStatus.PENDING, BookingStatus.CONFIRMED, BookingStatus.IN_SERVICE, BookingStatus.READY_FOR_DELIVERY)));
            
            try {
                ResponseEntity<Long> vRes = restTemplate.exchange("http://user-service/api/vehicles/user/" + customerId + "/count", HttpMethod.GET, entity, Long.class);
                summary.setTotalVehicles(vRes.getBody() != null ? vRes.getBody() : 0);
            } catch (Exception e) {}
        }
        
        return summary;
    }

    public DashboardSummaryDTO getDashboardSummaryFallback(String customerId, boolean isAdmin, Throwable t) {
        throw new RuntimeException("Dashboard is temporarily unavailable. Reason: " + t.getMessage());
    }

    public Long getActiveBookingsCountForService(Long serviceId) {
        return bookingRepository.countByServiceIdAndStatusIn(serviceId, Arrays.asList(
                BookingStatus.PENDING, BookingStatus.CONFIRMED, BookingStatus.IN_SERVICE, BookingStatus.READY_FOR_DELIVERY
        ));
    }
}
