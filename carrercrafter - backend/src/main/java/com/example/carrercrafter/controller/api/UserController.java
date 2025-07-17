package com.example.carrercrafter.controller.api;

import com.example.carrercrafter.Service.UserService;
import com.example.carrercrafter.dto.UserDto;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    
    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.saveUser(userDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasAnyRole('JOB_SEEKER', 'EMPLOYER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        UserDto userDto = userService.getUserById(id);
        return userDto != null ? ResponseEntity.ok(userDto) : ResponseEntity.notFound().build();
    }
    
    @PreAuthorize("hasAnyRole('JOB_SEEKER', 'EMPLOYER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @Valid @RequestBody UserDto updatedUserDto) {
        UserDto userDto = userService.updateUser(id, updatedUserDto);
        return userDto != null ? ResponseEntity.ok(userDto) : ResponseEntity.notFound().build();
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'JOB_SEEKER', 'EMPLOYER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUserAndRelated(id);
        return deleted ? ResponseEntity.noContent().build() :
                ResponseEntity.status(404).body("User not found");
    }
    
    
    
 

    /*@PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");
        UserDto userDto = userService.login(email, password);
        return userDto != null ? ResponseEntity.ok(userDto) : ResponseEntity.status(401).build();
    }*/
}
