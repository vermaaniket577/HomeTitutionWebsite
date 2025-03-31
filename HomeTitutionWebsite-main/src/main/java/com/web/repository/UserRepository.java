package com.web.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.security.core.userdetails.User; // Spring Security User
//❌ Remove this
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.Entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

 User findByUsername(com.web.Entity.User username);

 User save(User user);


 Optional<User> findByUsername(String username);
//User findByResetPasswordToken(String token);

public User getUserByUsername(@Param("username") String username);


//Object findByUsername(org.springframework.security.core.userdetails.User username);


}