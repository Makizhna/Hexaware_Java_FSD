package com.example.carrercrafter.controller.api;

import com.example.carrercrafter.Service.JobSeekerService;
import com.example.carrercrafter.Service.SavedJobsService;
import com.example.carrercrafter.dto.JobSeekerDto;
import com.example.carrercrafter.dto.SavedJobsDto;
import com.example.carrercrafter.entities.SavedJobs;
import com.example.carrercrafter.repository.SavedJobsRepository;



import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/savedjobs")
@CrossOrigin(origins = "http://localhost:3000")
public class SavedJobsController {

    @Autowired
    private SavedJobsService savedJobsService;
    
    
    @Autowired
    private JobSeekerService jobSeekerService;
    
    @Autowired
    private SavedJobsRepository savedJobsRepo;
    

    
    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PostMapping
    public ResponseEntity<SavedJobsDto> save(@RequestBody SavedJobsDto dto) {
    	System.out.println(" Incoming save request: jobId=" + dto.getJobId() + ", jobSeekerId=" + dto.getJobSeekerId());
        return ResponseEntity.ok(savedJobsService.save(dto));
    }

    
    @PreAuthorize("hasRole('JOB_SEEKER')")
    @GetMapping
    public ResponseEntity<List<SavedJobsDto>> getAll() {
        return ResponseEntity.ok(savedJobsService.getAll());
    }

    
    @PreAuthorize("hasRole('JOB_SEEKER')")
    @GetMapping("/{id}")
    public ResponseEntity<SavedJobsDto> getById(@PathVariable int id) {
        SavedJobsDto dto = savedJobsService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    
    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PutMapping("/{id}")
    public ResponseEntity<SavedJobsDto> update(@PathVariable int id, @RequestBody SavedJobsDto dto) {
        SavedJobsDto updated = savedJobsService.updateSavedJob(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    
    @PreAuthorize("hasRole('JOB_SEEKER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        savedJobsService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    
    @GetMapping("/seeker/{seekerId}")
    public ResponseEntity<List<SavedJobsDto>> getByJobSeeker(
        @PathVariable int seekerId,
        Authentication authentication) {

        JobSeekerDto dto = jobSeekerService.getJobSeekerById(seekerId);

        if (dto == null || dto.getUser() == null) {
            return ResponseEntity.notFound().build();
        }

        //  TEMPORARILY disable email match check
        // if (!dto.getUser().getEmail().equals(authentication.getName())) {
        //     System.out.println(" Unauthorized: Email mismatch");
        //     return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        // }

        System.out.println(" Authenticated user: " + authentication.getName());
        return ResponseEntity.ok(savedJobsService.getByJobSeeker(seekerId));
    }
    
    
    
    
    
    
    
    
  





}
