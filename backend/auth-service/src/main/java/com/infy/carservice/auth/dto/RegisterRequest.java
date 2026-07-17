package com.infy.carservice.auth.dto;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
@Data
public class RegisterRequest {
    @NotBlank(message = "Please provide a valid email")
    @Email(message = "Please provide a valid email")
    private String email;
    @NotBlank(message = "Please provide a valid password")
    private String password;
    @NotBlank(message = "Please provide a valid fullName")
    private String fullName;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
    
}
