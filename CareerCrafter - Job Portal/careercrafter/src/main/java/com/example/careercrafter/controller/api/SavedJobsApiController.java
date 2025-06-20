package com.example.careercrafter.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.careercrafter.entity.JobSeekers;
import com.example.careercrafter.entity.Jobs;
import com.example.careercrafter.entity.SavedJobs;
import com.example.careercrafter.repo.JobSeekerRepository;
import com.example.careercrafter.repo.JobsRepository;
import com.example.careercrafter.repo.SavedJobsRepository;

@RestController
@RequestMapping("/api/savedjobs")
public class SavedJobsApiController {

    @Autowired
    private SavedJobsRepository savedRepo;

    @Autowired
    private JobSeekerRepository seekerRepo;

    @Autowired
    private JobsRepository jobRepo;

    @PostMapping
    public ResponseEntity<?> saveJob(@RequestBody SavedJobs saved) {
        Optional<JobSeekers> seeker = seekerRepo.findById(saved.getJobSeeker().getSeekerId());
        Optional<Jobs> job = jobRepo.findById(saved.getJob().getJobId());

        if (seeker.isEmpty() || job.isEmpty()) return new ResponseEntity<>("Invalid job or seeker", HttpStatus.NOT_FOUND);

        saved.setJobSeeker(seeker.get());
        saved.setJob(job.get());
        return new ResponseEntity<>(savedRepo.save(saved), HttpStatus.CREATED);
    }

    @GetMapping
    public List<SavedJobs> getAll() {
        return savedRepo.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if (!savedRepo.existsById(id)) return new ResponseEntity<>("Saved job not found", HttpStatus.NOT_FOUND);
        savedRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
