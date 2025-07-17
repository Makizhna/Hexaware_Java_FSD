package com.example.carrercrafter.controller.api;

import com.example.carrercrafter.Service.MyDBUserDetailsService;
import com.example.carrercrafter.Service.UserService;
import com.example.carrercrafter.dto.AuthRequest;
import com.example.carrercrafter.dto.AuthResponse;
import com.example.carrercrafter.security.JwtUtil;
import com.example.carrercrafter.entities.User;
import com.example.carrercrafter.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private MyDBUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    
   

    @Autowired
    private UserRepository userRepo;
    

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;


    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(), authRequest.getPassword()
                )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        User user = userRepo.findByEmail(authRequest.getEmail())
                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

       
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name()); 
        return ResponseEntity.ok(new AuthResponse(token, user.getRole().name(), user.getId()));


    }
    
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        User savedUser = userRepo.save(user);
        return ResponseEntity.ok(savedUser);
    }
    
    

    
    /*@PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        boolean success = userService.resetPasswordWithToken(token, newPassword);
        return success
            ? ResponseEntity.ok("Password reset successful")
            : ResponseEntity.status(400).body("Invalid or expired token");
    }

   
    
    

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            String token = userService.generatePasswordResetToken(email);
            String resetUrl = "http://localhost:3000/reset-password?token=" + token;

            mailService.sendPasswordResetEmail(email, resetUrl);

            return ResponseEntity.ok("Reset link sent to your email.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Email not found");
        }
    }*/

  


    
}
