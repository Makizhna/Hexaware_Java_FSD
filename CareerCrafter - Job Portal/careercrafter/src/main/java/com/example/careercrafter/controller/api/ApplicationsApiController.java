package com.example.careercrafter.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.careercrafter.entity.Applications;
import com.example.careercrafter.entity.JobSeekers;
import com.example.careercrafter.entity.Jobs;
import com.example.careercrafter.repo.ApplicationRepository;
import com.example.careercrafter.repo.JobSeekerRepository;
import com.example.careercrafter.repo.JobsRepository;

@RestController
@RequestMapping("/api/applications")
public class ApplicationsApiController {

    @Autowired
    private ApplicationRepository appRepo;

    @Autowired
    private JobsRepository jobRepo;

    @Autowired
    private JobSeekerRepository seekerRepo;

    @PostMapping
    public ResponseEntity<?> apply(@RequestBody Applications app) {
        if (app.getJob() == null || app.getJob().getJobId() == 0 ||
            app.getJobSeeker() == null || app.getJobSeeker().getSeekerId() == 0) {
            return new ResponseEntity<>("Job and JobSeeker IDs are required", HttpStatus.BAD_REQUEST);
        }

        Optional<Jobs> job = jobRepo.findById(app.getJob().getJobId());
        Optional<JobSeekers> seeker = seekerRepo.findById(app.getJobSeeker().getSeekerId());

        if (job.isEmpty() || seeker.isEmpty()) return new ResponseEntity<>("Invalid references", HttpStatus.NOT_FOUND);

        app.setJob(job.get());
        app.setJobSeeker(seeker.get());
        return new ResponseEntity<>(appRepo.save(app), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Applications> getAll() {
        return appRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Applications> getOne(@PathVariable int id) {
        return appRepo.findById(id)
                .map(app -> new ResponseEntity<>(app, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
