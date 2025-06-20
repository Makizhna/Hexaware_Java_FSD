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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.careercrafter.entity.Employers;
import com.example.careercrafter.entity.Jobs;
import com.example.careercrafter.repo.EmployerRepository;
import com.example.careercrafter.repo.JobsRepository;

@RestController
@RequestMapping("/api/jobs")
public class JobsApiController {

    @Autowired
    private JobsRepository jobRepo;

    @Autowired
    private EmployerRepository employerRepo;

    @PostMapping
    public ResponseEntity<?> createJob(@RequestBody Jobs job) {
        if (job.getPostedBy() == null || job.getPostedBy().getEmployeeId() == 0) {
            return new ResponseEntity<>("Employer ID required", HttpStatus.BAD_REQUEST);
        }

        Optional<Employers> emp = employerRepo.findById(job.getPostedBy().getEmployeeId());
        if (emp.isEmpty()) return new ResponseEntity<>("Employer not found", HttpStatus.NOT_FOUND);

        job.setPostedBy(emp.get());
        return new ResponseEntity<>(jobRepo.save(job), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Jobs> getAllJobs() {
        return jobRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jobs> getJob(@PathVariable int id) {
        return jobRepo.findById(id)
                .map(job -> new ResponseEntity<>(job, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable int id, @RequestBody Jobs req) {
        Optional<Jobs> optional = jobRepo.findById(id);

        if (optional.isPresent()) {
            Jobs job = optional.get();
            job.setTitle(req.getTitle());
            job.setDescription(req.getDescription());
            job.setLocation(req.getLocation());
            return ResponseEntity.ok(jobRepo.save(job));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable int id) {
        if (!jobRepo.existsById(id)) return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
        jobRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

