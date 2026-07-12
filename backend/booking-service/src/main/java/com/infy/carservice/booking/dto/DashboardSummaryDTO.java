package com.infy.carservice.booking.dto;
import lombok.Data;
@Data
public class DashboardSummaryDTO {
    private long totalVehicles;
    private long totalBookings;
    private long activeBookings;
    private long completedBookings;
    // Admin specific
    private long totalCustomers;
    private long totalCategories;
    private long totalServices;
}
