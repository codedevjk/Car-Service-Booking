package com.infy.carservice.catalog.dto;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import com.infy.carservice.catalog.entity.AvailabilityStatus;


public class ServicePackageDTO {
    private Long id;
    private Long categoryId;
    @NotBlank(message = "Please provide a valid name")
    private String name;
    private String description;
    private BigDecimal price;
    private AvailabilityStatus availabilityStatus;
	public ServicePackageDTO() {
		super();
	}
	public ServicePackageDTO(Long id, Long categoryId, @NotBlank(message = "Please provide a valid name") String name,
			String description, BigDecimal price, AvailabilityStatus availabilityStatus) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.availabilityStatus = availabilityStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public AvailabilityStatus getAvailabilityStatus() {
		return availabilityStatus;
	}
	public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}
	 
}

