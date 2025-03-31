package com.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.Entity.User;
import com.web.dto.UserDto;

public interface UserRepository extends JpaRepository<User, Long> {

 User findByUsername(String username);

 User save(UserDto userDto);
}