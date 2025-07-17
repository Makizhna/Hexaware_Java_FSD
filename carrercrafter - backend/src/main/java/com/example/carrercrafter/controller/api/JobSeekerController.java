package com.example.carrercrafter.controller.api;

import com.example.carrercrafter.Service.JobSeekerService;
import com.example.carrercrafter.dto.JobSeekerDto;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/jobseeker")
public class JobSeekerController {

    @Autowired
    private JobSeekerService jobSeekerService;

    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PostMapping
    public ResponseEntity<JobSeekerDto> create(@Valid @RequestBody JobSeekerDto dto) {
        return ResponseEntity.ok(jobSeekerService.saveJobSeeker(dto));
    }

    @PreAuthorize("hasAnyRole('EMPLOYER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<JobSeekerDto>> getAll() {
        return ResponseEntity.ok(jobSeekerService.getAllJobSeekers());
    }

    @PreAuthorize("hasAnyRole('JOB_SEEKER', 'EMPLOYER')")
    @GetMapping("/{id}")
    public ResponseEntity<JobSeekerDto> getById(@PathVariable int id, Authentication authentication) {
        JobSeekerDto existing = jobSeekerService.getJobSeekerById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        String email = authentication.getName();

        // Allow job seeker to view their own profile, or allow employers to view any
        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_JOB_SEEKER"))) {
            if (!existing.getUser().getEmail().equals(email)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        return ResponseEntity.ok(existing);
    }


    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PutMapping("/{id}")
    public ResponseEntity<JobSeekerDto> update(@PathVariable int id, @Valid @RequestBody JobSeekerDto dto, Authentication authentication){
    
    	// String email = authentication.getName();
    	    JobSeekerDto existing = jobSeekerService.getJobSeekerById(id);

    	    // Check if the logged-in user is the same as the job seeker's user
    	    if (!isOwner(existing, authentication)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
  
        JobSeekerDto updated = jobSeekerService.updateJobSeeker(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }


    @PreAuthorize("hasRole('JOB_SEEKER')")
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<JobSeekerDto> getByUserId(@PathVariable int userId) {
        JobSeekerDto dto = jobSeekerService.getByUserId(userId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('EMPLOYER', 'JOB_SEEKER')")
    @GetMapping("/search")
    public ResponseEntity<List<JobSeekerDto>> search(
            @RequestParam(required = false) String skills,
            @RequestParam(required = false) String experience) {
        return ResponseEntity.ok(jobSeekerService.searchBySkillsAndExperience(skills, experience));
    }
    
    @PreAuthorize("hasAnyRole('EMPLOYER', 'JOB_SEEKER')")
    @GetMapping("/search/skill/{skill}")
    public ResponseEntity<List<JobSeekerDto>> searchBySkill(@PathVariable String skill) {
        return ResponseEntity.ok(jobSeekerService.searchBySkill(skill));
    }
    
    @PreAuthorize("hasRole('JOB_SEEKER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id,Authentication authentication) {
    	JobSeekerDto existing = jobSeekerService.getJobSeekerById(id);

        if (!isOwner(existing,authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        jobSeekerService.deleteJobSeeker(id);
        return ResponseEntity.noContent().build();
    }

    
    private boolean isOwner(JobSeekerDto dto, Authentication auth) {
        return dto != null && dto.getUser() != null &&
               dto.getUser().getEmail().equals(auth.getName());
    }

}
