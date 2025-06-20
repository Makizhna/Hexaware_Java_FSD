package com.example.careercrafter.controller.api;

import com.example.careercrafter.entity.Employers;
import com.example.careercrafter.entity.Users;
import com.example.careercrafter.repo.EmployerRepository;
import com.example.careercrafter.repo.JobSeekerRepository;
import com.example.careercrafter.repo.JobsRepository;
import com.example.careercrafter.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmployerRepository employerRepo;

    @Autowired
    private JobSeekerRepository jobSeekerRepo;

    @Autowired
    private JobsRepository jobRepo;

    
    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users saved = userRepo.save(user);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUser(@PathVariable int id) {
        return userRepo.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable int id, @RequestBody Users updatedUser) {
        Optional<Users> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            Users saved = userRepo.save(user);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        // Delete jobs if user is an employer
        if (employerRepo.existsByUser_Id(id)) {
            Employers employer = employerRepo.findByUser_Id(id);
            jobRepo.deleteByPostedBy(employer);
            employerRepo.deleteByUser_Id(id);
        }

        
        if (jobSeekerRepo.existsByUser_Id(id)) {
            jobSeekerRepo.deleteByUser_Id(id);
        }

        
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
