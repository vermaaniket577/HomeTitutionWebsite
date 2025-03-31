package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.web.Entity.User;
import com.web.dto.UserDto;
import com.web.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

 @Autowired
 PasswordEncoder passwordEncoder;

 private UserRepository userRepository;

 public UserServiceImpl(UserRepository userRepository) {
  super();
  this.userRepository = userRepository;
 }



 @Override
 public User save(UserDto userDto) {
  User user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()),
    userDto.getFullname());
  return userRepository.save(user);
 }



@Override
public User findByUsername(String username) {
	// TODO Auto-generated method stub
	return null;
}



}