package com.example.careercrafter.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.careercrafter.entity.JobSeekers;
import com.example.careercrafter.entity.Users;
import com.example.careercrafter.repo.JobSeekerRepository;
import com.example.careercrafter.repo.UserRepository;

@RestController
@RequestMapping("/api/jobseekers")
public class JobSeekerApiController {

    @Autowired
    private JobSeekerRepository jobSeekerRepo;

    @Autowired
    private UserRepository userRepo;

    
    @PostMapping
    public ResponseEntity<?> createJobSeeker(@RequestBody JobSeekers js) {
        if (js.getUser() == null || js.getUser().getId() == 0) {
            return new ResponseEntity<>("User ID is required", HttpStatus.BAD_REQUEST);
        }

        Optional<Users> user = userRepo.findById(js.getUser().getId());
        if (user.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        js.setUser(user.get());
        js.setSeekerId(0); 

        return new ResponseEntity<>(jobSeekerRepo.save(js), HttpStatus.CREATED);
    }

    
    @GetMapping
    public ResponseEntity<List<JobSeekers>> getAll() {
        return new ResponseEntity<>(jobSeekerRepo.findAll(), HttpStatus.OK);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<JobSeekers> getOne(@PathVariable int id) {
        return jobSeekerRepo.findById(id)
                .map(js -> new ResponseEntity<>(js, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody JobSeekers req) {
        Optional<JobSeekers> optional = jobSeekerRepo.findById(id);

        if (optional.isPresent()) {
            JobSeekers js = optional.get();
            js.setName(req.getName());
            js.setDob(req.getDob());
            js.setPhone(req.getPhone());
            js.setEducation(req.getEducation());
            js.setExperience(req.getExperience());
            js.setSkills(req.getSkills());
            js.setResumeUrl(req.getResumeUrl());

            JobSeekers saved = jobSeekerRepo.save(js);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("JobSeeker not found", HttpStatus.NOT_FOUND);
        }
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Optional<JobSeekers> optional = jobSeekerRepo.findById(id);

        if (optional.isPresent()) {
            jobSeekerRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("JobSeeker not found", HttpStatus.NOT_FOUND);
        }
    }
}
