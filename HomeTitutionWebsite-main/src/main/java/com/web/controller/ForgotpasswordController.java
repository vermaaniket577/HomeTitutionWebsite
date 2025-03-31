package com.web.controller;
import java.net.PasswordAuthentication;
import java.security.SecureRandom;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.Entity.User;

import com.web.config.CustomUserDetailsService;
import com.web.dto.UserDto;
import com.web.repository.UserRepository;
//import com.web.service.ForgotPasswordService;
import com.web.service.ForgotPasswordService;
import com.web.service.UserService;

import jakarta.servlet.http.HttpSession;
//import ch.qos.logback.core.model.Model;

@Controller
public class ForgotpasswordController {
  @Autowired
   CustomUserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
	//Random random = new Random(1000);

  
    @Autowired
    private ForgotPasswordService forgotPasswordService;
    
    private static final long OTP_VALIDITY_DURATION = 3 * 60 * 1000; // 5 minutes in milliseconds
    @RequestMapping("/forgot-password")
    public String openEmailForm(Model model){
    	   model.addAttribute("userDto", new UserDto()); 
        return "forgot-password";   
    }
    
@PostMapping("/send-otp")
  

    public String sendOTP( @RequestParam("username") String username, HttpSession session, RedirectAttributes redirectAttributes) {
      
    	  try {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Generate 6-digit OTP (fixed range)
        
        Random random = new SecureRandom();
        Integer otp = 100000 + new SecureRandom().nextInt(999999); // Generates 6-digit Otp
       
        // Send OTP to email (implementation needed)
        
        session.setAttribute("otp", otp);
        session.setAttribute("username", username);
        session.setAttribute("otpTime", System.currentTimeMillis()); // Add this line

        System.out.println("Generated OTP: " + otp);
        System.out.println("Stored OTP in session: " + session.getAttribute("otp"));

        
        String subject="OTP From Home Titution";
        String message=""
        +"<div style='border:1px solid #e2e2e2; padding:20px'>"

        +"<h1>"
        +"<OTP is>"
        +"<b>"+otp
        +"</n>"
        +"</h1>"
        +"</div>";
        String to=username;
       
       boolean emailSent= this.forgotPasswordService.sendOTPEmail(subject, message, to );
       System.out.println("Email sending status: " + emailSent);
      
     
       if (emailSent) {
    	// Redirect to verify-otp on success
           redirectAttributes.addFlashAttribute("success", "OTP sent successfully!");
           session.setAttribute("message", "Email Sent Your Mail Id !");
         //  System.out.println("send otp page");
           return "redirect:/verify-otp";
        
    	
       } else {
   // Remove session attributes if email failed to send
    	   
           session.removeAttribute("otp");
           session.removeAttribute("username");
           redirectAttributes.addFlashAttribute("error", "Failed to send OTP. Email Not Registered.");
           return "redirect:/forgot-password";
       }
       
    } catch (UsernameNotFoundException e) {
        redirectAttributes.addFlashAttribute("error", "Email not registered");
        return "redirect:/forgot-password";
    }
    }
        
 
 
	
@GetMapping("/verify-otp")
public String showVerifyOtpPage(HttpSession session, 
                               RedirectAttributes redirectAttributes, 
                               Model model) {
    Long otpTime = (Long) session.getAttribute("otpTime");
    String username = (String) session.getAttribute("username");

    // Check if session attributes exist
    if (otpTime == null || username == null) {
        redirectAttributes.addFlashAttribute("error", "Session expired");
        return "redirect:/forgot-password";
    }

    long remainingTime = OTP_VALIDITY_DURATION - (System.currentTimeMillis() - otpTime);
    if (remainingTime <= 0) {
        redirectAttributes.addFlashAttribute("error", "OTP expired");
        return "redirect:/forgot-password";
    }

    model.addAttribute("remainingTime", remainingTime / 1000);
    model.addAttribute("userDto", new UserDto());
    return "verify-otp";
}

@PostMapping("/verify-otp")
public String verifyOtp(@RequestParam("otp") Integer enteredOtp,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
    Integer storedOtp = (Integer) session.getAttribute("otp");
    Long otpTime = (Long) session.getAttribute("otpTime");
    String username = (String) session.getAttribute("username"); // Get username from session

    if (storedOtp == null || otpTime == null) {
        redirectAttributes.addFlashAttribute("error", "Session expired");
        return "redirect:/forgot-password";
    }
    else if (System.currentTimeMillis() - otpTime > OTP_VALIDITY_DURATION) {
        redirectAttributes.addFlashAttribute("error", "OTP expired");
        return "redirect:/verify-otp";
    }

    else if (enteredOtp.equals(storedOtp)) {
        // Clear OTP data from session
        session.removeAttribute("otp");
        session.removeAttribute("otpTime");
        
        // Keep username for password reset
        session.setAttribute("resetUser", username);
        return "redirect:/reset-password";

    } 
    else {
        redirectAttributes.addFlashAttribute("error", "Invalid OTP");
        return "redirect:/verify-otp";
    }
}

@GetMapping("/resend-otp")
public String resendOTP(HttpSession session, RedirectAttributes redirectAttributes) {
    String username = (String) session.getAttribute("username");
    if (username == null) {
        redirectAttributes.addFlashAttribute("error", "Session expired");
        return "redirect:/forgot-password";
    }

    // Generate new OTP
    Integer newOtp = 100000 + new SecureRandom().nextInt(99999);
    session.setAttribute("otp", newOtp);
    session.setAttribute("otpTime", System.currentTimeMillis());

    String subject="OTP From Home Titution";
    String message=""
    +"<div style='border:1px solid #e2e2e2; padding:20px'>"

    +"<h1>"
    +"<OTP is>"
    +"<b>"+newOtp
    +"</n>"
    +"</h1>"
    +"</div>";
    String to=username;
    // Resend email logic...
    boolean emailresend= this.forgotPasswordService.sendOTPEmail(username, message, to );
    System.out.println("Email resending status: " + emailresend);


    redirectAttributes.addFlashAttribute("success", "New OTP sent!");
    return "redirect:/verify-otp";
}

@GetMapping("/reset-password")
public String showResetForm(HttpSession session, Model model) {
    // Session validation
    if (session.getAttribute("resetUser") == null) {
        return "redirect:/forgot-password";
    }
    
    // This name must match th:object in template
    model.addAttribute("UserDto", new UserDto());
    return "reset-password";
}

@PostMapping("/reset-password")
public String handleReset(
    @ModelAttribute("resetRequest") UserDto userdto,
    BindingResult bindingResult,
    HttpSession session,RedirectAttributes redirectAttributes
) {
	 String username = (String) session.getAttribute("resetUser");
     if (username == null) {
         redirectAttributes.addFlashAttribute("error", "Session expired");
         return "redirect:/forgot-password";
     }

	  // 2. Check for form validation errors
    if (bindingResult.hasErrors()) {
        return "reset-password";
    }

    // 3. Validate password match
    if (!userdto.getNewPassword().equals(userdto.getConfirmPassword())) {
        bindingResult.rejectValue("confirmPassword", "mismatch", "Passwords do not match");
        return "reset-password";
    }

    try {
    	forgotPasswordService.updatePassword(username, userdto.getNewPassword());
        session.invalidate(); // Clear all session data
        redirectAttributes.addFlashAttribute("success", "Password reset successfully");
        return "redirect:/login";
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Password reset failed: " + e.getMessage());
        return "redirect:/reset-password";
    }
  
}
    
    }


