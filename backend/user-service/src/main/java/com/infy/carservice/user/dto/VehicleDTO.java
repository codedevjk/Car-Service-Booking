package com.infy.carservice.user.dto;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class VehicleDTO {
    private Long id;
    private String userId;
    
    @NotBlank(message = "Please provide a valid registrationNumber")
    @jakarta.validation.constraints.Pattern(regexp = "^[A-Z]{2}[A-Za-z0-9]{6}$", message = "Please provide a valid registrationNumber")
    private String registrationNumber;
    
    @NotBlank(message = "Please provide a valid manufacturer")
    private String manufacturer;
    
    @NotBlank(message = "Please provide a valid model")
    private String model;
    
    private String fuelType;
    
    private Integer manufacturingYear;

	public VehicleDTO(Long id, String userId,
			@NotBlank(message = "Please provide a valid registrationNumber") @Pattern(regexp = "^[A-Z]{2}[A-Za-z0-9]{6}$", message = "Please provide a valid registrationNumber") String registrationNumber,
			@NotBlank(message = "Please provide a valid manufacturer") String manufacturer,
			@NotBlank(message = "Please provide a valid model") String model, String fuelType,
			Integer manufacturingYear) {
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

	@Override
	public String toString() {
		return "VehicleDTO [id=" + id + ", userId=" + userId + ", registrationNumber=" + registrationNumber
				+ ", manufacturer=" + manufacturer + ", model=" + model + ", fuelType=" + fuelType
				+ ", manufacturingYear=" + manufacturingYear + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fuelType, id, manufacturer, manufacturingYear, model, registrationNumber, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleDTO other = (VehicleDTO) obj;
		return Objects.equals(fuelType, other.fuelType) && Objects.equals(id, other.id)
				&& Objects.equals(manufacturer, other.manufacturer)
				&& Objects.equals(manufacturingYear, other.manufacturingYear) && Objects.equals(model, other.model)
				&& Objects.equals(registrationNumber, other.registrationNumber) && Objects.equals(userId, other.userId);
	}
    
    
}
