package com.web.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	 private String username;
	 private String password;
	 private String fullname;
	 private LocalDateTime tokenCreationDate;
	 private Long token;
	 private Integer otp;
	  private String newPassword;
	    private String confirmPassword;

	    // REQUIRED: No-argument constructor
	   

	  
	 public UserDto(String username, String password, String fullname) {
	  super();
	  this.username = username;
	  this.password = password;
	  this.fullname = fullname;
	 }

	 public UserDto() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
	  return username;
	 }

	 public void setUsername(String username) {
	  this.username = username;
	 }

	 public String getPassword() {
	  return password;
	 }

	 public void setPassword(String password) {
	  this.password = password;
	 }

	 public String getFullname() {
	  return fullname;
	 }

	 public void setFullname(String fullname) {
	  this.fullname = fullname;
	 }

	 public LocalDateTime getTokenCreationDate() {
		return tokenCreationDate;
	}

	public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
		this.tokenCreationDate = tokenCreationDate;
	}

	public Long getToken() {
		return token;
	}

	public void setToken(Long token) {
		this.token = token;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public UserDto(Integer otp) {
		super();
		this.otp = otp;
	}
	  // Getters and setters
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
	@Override
	 public String toString() {
	  return "UserDto [username=" + username + ", password=" + password + ", fullname=" + fullname + "]";
	 }}
