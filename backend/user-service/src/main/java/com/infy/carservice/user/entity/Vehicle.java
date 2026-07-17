package com.infy.carservice.user.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userId;
    
    @Column(unique = true, nullable = false)
    private String registrationNumber;
    
    @Column(nullable = false)
    private String manufacturer;
    
    @Column(nullable = false)
    private String model;
    
    private String fuelType;
    
    private Integer manufacturingYear;

	public Vehicle(Long id, String userId, String registrationNumber, String manufacturer, String model,
			String fuelType, Integer manufacturingYear) {
		super();
		this.id = id;
		this.userId = userId;
		this.registrationNumber = registrationNumber;
		this.manufacturer = manufacturer;
		this.model = model;
		this.fuelType = fuelType;
		this.manufacturingYear = manufacturingYear;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public Integer getManufacturingYear() {
		return manufacturingYear;
	}

	public void setManufacturingYear(Integer manufacturingYear) {
		this.manufacturingYear = manufacturingYear;
	}
    
    
}
