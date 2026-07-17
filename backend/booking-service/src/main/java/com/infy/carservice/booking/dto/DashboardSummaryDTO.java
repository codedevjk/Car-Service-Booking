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
    
    
	public DashboardSummaryDTO() {
		super();
	}
	 
	public long getTotalVehicles() {
		return totalVehicles;
	}
	public void setTotalVehicles(long totalVehicles) {
		this.totalVehicles = totalVehicles;
	}
	public long getTotalBookings() {
		return totalBookings;
	}
	public void setTotalBookings(long totalBookings) {
		this.totalBookings = totalBookings;
	}
	public long getActiveBookings() {
		return activeBookings;
	}
	public void setActiveBookings(long activeBookings) {
		this.activeBookings = activeBookings;
	}
	public long getCompletedBookings() {
		return completedBookings;
	}
	public void setCompletedBookings(long completedBookings) {
		this.completedBookings = completedBookings;
	}
	public long getTotalCustomers() {
		return totalCustomers;
	}
	public void setTotalCustomers(long totalCustomers) {
		this.totalCustomers = totalCustomers;
	}
	public long getTotalCategories() {
		return totalCategories;
	}
	public void setTotalCategories(long totalCategories) {
		this.totalCategories = totalCategories;
	}
	public long getTotalServices() {
		return totalServices;
	}
	public void setTotalServices(long totalServices) {
		this.totalServices = totalServices;
	}
    
    
}
