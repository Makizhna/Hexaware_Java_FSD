package com.example.carrercrafter.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.carrercrafter.Service.JobSeekerResumeService;
import com.example.carrercrafter.dto.JobSeekerResumeDto;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/jobseeker-resume")
public class JobSeekerResumeController {

    @Autowired
    private JobSeekerResumeService resumeService;

    
    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PostMapping("/upload")
    public ResponseEntity<JobSeekerResumeDto> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jobSeekerId") int jobSeekerId) {

        JobSeekerResumeDto saved = resumeService.uploadResume(file, jobSeekerId);
        return ResponseEntity.ok(saved);
    }


    @PreAuthorize("hasRole('JOB_SEEKER')")
    @GetMapping("/{seekerId}")
    public ResponseEntity<JobSeekerResumeDto> getResume(@PathVariable int seekerId) {
        JobSeekerResumeDto dto = resumeService.getResumeBySeekerId(seekerId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PutMapping("/{seekerId}")
    public ResponseEntity<JobSeekerResumeDto> updateResume(@PathVariable int seekerId, @RequestBody JobSeekerResumeDto dto) {
        JobSeekerResumeDto updated = resumeService.updateResume(seekerId, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('JOB_SEEKER')")
    @DeleteMapping("/{seekerId}")
    public ResponseEntity<Void> deleteResume(@PathVariable int seekerId) {
        resumeService.deleteResume(seekerId);
        return ResponseEntity.noContent().build();
    }
}