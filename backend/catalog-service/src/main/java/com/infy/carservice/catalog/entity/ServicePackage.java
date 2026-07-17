package com.infy.carservice.catalog.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
@Entity
public class ServicePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long categoryId;
    @Column(nullable = false)
    private String name;
    private String description;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
    
    
	public ServicePackage() {
		super();
	}


	public ServicePackage(Long id, Long categoryId, String name, String description, BigDecimal price,
			AvailabilityStatus availabilityStatus) {
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
