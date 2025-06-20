package com.example.careercrafter.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.careercrafter.entity.Employers;
import com.example.careercrafter.entity.Users;
import com.example.careercrafter.repo.EmployerRepository;
import com.example.careercrafter.repo.UserRepository;

@RestController
@RequestMapping("/api/employers")
public class EmployerApiController {

    @Autowired
    private EmployerRepository employerRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Employers emp) {
        if (emp.getUser() == null || emp.getUser().getId() == 0) {
            return new ResponseEntity<>("User ID is required", HttpStatus.BAD_REQUEST);
        }

        Optional<Users> optionalUser = userRepo.findById(emp.getUser().getId());
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        emp.setUser(optionalUser.get());     // Attach the existing user
        emp.setEmployeeId(0);                // Ensure it's a new insert (auto-generate ID)

        Employers saved = employerRepo.save(emp);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employers>> getAll() {
        return new ResponseEntity<>(employerRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employers> getOne(@PathVariable int id) {
        return employerRepo.findById(id)
                .map(emp -> new ResponseEntity<>(emp, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Employers req) {
        Optional<Employers> optional = employerRepo.findById(id);

        if (optional.isPresent()) {
            Employers emp = optional.get();
            emp.setName(req.getName());
            emp.setPosition(req.getPosition());
            emp.setCompanyName(req.getCompanyName());
            emp.setLocation(req.getLocation());

            Employers saved = employerRepo.save(emp);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employer not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Optional<Employers> optional = employerRepo.findById(id);

        if (optional.isPresent()) {
            employerRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Employer not found", HttpStatus.NOT_FOUND);
        }
    }
}
