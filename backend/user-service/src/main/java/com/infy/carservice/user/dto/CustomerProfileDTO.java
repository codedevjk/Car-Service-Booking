package com.infy.carservice.user.dto;

import java.util.Objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CustomerProfileDTO {
    private String userId;
    
    @NotBlank(message = "Please provide a valid fullName")
    private String fullName;

    @NotBlank(message = "Please provide a valid email")
    @jakarta.validation.constraints.Email(message = "Please provide a valid email")
    private String email;

    private String contactNumber;
    
    private String address;

	public CustomerProfileDTO(String userId, @NotBlank(message = "Please provide a valid fullName") String fullName,
			@NotBlank(message = "Please provide a valid email") @Email(message = "Please provide a valid email") String email,
			String contactNumber, String address) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.contactNumber = contactNumber;
		this.address = address;
	}

	public CustomerProfileDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "CustomerProfileDTO [userId=" + userId + ", fullName=" + fullName + ", email=" + email
				+ ", contactNumber=" + contactNumber + ", address=" + address + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, contactNumber, email, fullName, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerProfileDTO other = (CustomerProfileDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(contactNumber, other.contactNumber)
				&& Objects.equals(email, other.email) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(userId, other.userId);
	}
    
    
}
