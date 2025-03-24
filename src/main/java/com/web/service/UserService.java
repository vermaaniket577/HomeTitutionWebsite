package com.web.service;

import com.web.Entity.User;
import com.web.dto.UserDto;

public interface UserService {
 User findByUsername(String username);

 User save(UserDto userDto);

}