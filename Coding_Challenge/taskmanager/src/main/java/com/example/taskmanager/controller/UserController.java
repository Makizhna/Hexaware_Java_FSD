package com.example.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    
    @GetMapping("/")
    public String welcome() {
        return "Welcome to Task Manager!";
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_ADMIN");
        User saved = userRepo.save(user);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        User user = userRepo.getReferenceById(id);
        user.setPassword("hidden");
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepo.findAll();
        for (User u : users) {
            u.setPassword("hidden");
        }
        return ResponseEntity.ok(users);
    }

}
