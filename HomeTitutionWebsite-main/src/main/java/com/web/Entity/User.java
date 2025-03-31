package com.web.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@NoArgsConstructor 
@Table(name = "users")
public class User {
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Long id;
 // @Column(unique = true)
 private String username;
 private String password;
 private String fullname;

 @Column(length = 100)
 private Long token;
 @Column(columnDefinition = "TIMESTAMP")
 private LocalDateTime tokenCreationDate;
 
 public User() {
		super();
	}


 public User(String username, String password, String fullname) {
  super();
  this.username = username;
  this.password = password;
  this.fullname = fullname;
 }

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
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
 

 public Long getToken() {
     return token;
 }

 public void setToken(Long token) {
     this.token = token;
 }

 public LocalDateTime getTokenCreationDate() {
     return tokenCreationDate;
 }

 public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
     this.tokenCreationDate = tokenCreationDate;
 }


 @Override
 public String toString() {
  return "User [id=" + id + ", username=" + username + ", password=" + password + ", fullname=" + fullname + "]";
 }

public void setResetPasswordToken(String token2) {
	// TODO Auto-generated method stub
	
}

public void save(User user) {
	// TODO Auto-generated method stub
	
}




}