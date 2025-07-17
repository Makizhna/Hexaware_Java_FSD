package com.example.carrercrafter.controller.api;

import com.example.carrercrafter.Service.JobsService;
import com.example.carrercrafter.dto.JobsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:3000")
public class JobsController {

    @Autowired
    private JobsService jobsService;
    
    
    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping
    public ResponseEntity<JobsDto> create(@RequestBody JobsDto dto) {
        return ResponseEntity.ok(jobsService.saveJob(dto));
    }

    //@PreAuthorize("hasAnyRole('EMPLOYER', 'JOB_SEEKER')")
    @GetMapping
    public ResponseEntity<List<JobsDto>> getAll() {
        return ResponseEntity.ok(jobsService.getAllJobs());
    }
    
    
    //@PreAuthorize("hasAnyRole('EMPLOYER', 'JOB_SEEKER')")
    @GetMapping("/{id}")
    public ResponseEntity<JobsDto> getById(@PathVariable int id) {
        JobsDto dto = jobsService.getJobById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }
    

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/employer/{employerId}")
    public ResponseEntity<List<JobsDto>> getJobsByEmployer(@PathVariable int employerId) {
        List<JobsDto> jobs = jobsService.getJobsByEmployer(employerId);
        return ResponseEntity.ok(jobs);
    }

    
    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{id}")
    public ResponseEntity<JobsDto> update(@PathVariable int id, @RequestBody JobsDto dto) {
        JobsDto updated = jobsService.updateJob(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('EMPLOYER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        jobsService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
    
    
    
    
    
    @GetMapping("/recommend/{seekerId}")
    public ResponseEntity<List<JobsDto>> getRecommendedJobs(@PathVariable int seekerId) {
        return ResponseEntity.ok(jobsService.getRecommendedJobsForSeeker(seekerId));
    }

}
