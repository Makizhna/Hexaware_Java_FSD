package com.example.carrercrafter.controller.api;

import com.example.carrercrafter.Service.ApplicationsService;
import com.example.carrercrafter.dto.ApplicationsDto;
import com.example.carrercrafter.enums.ApplicationStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:3000")
public class ApplicationsController {

    @Autowired
    private ApplicationsService applicationsService;

    @PostMapping
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<ApplicationsDto> applyToJob(@RequestBody Map<String, Object> payload) {
    	
    	
        ApplicationsDto dto = new ApplicationsDto();

        dto.setJobId((int) payload.get("jobId"));
        dto.setJobSeekerId((int) payload.get("jobSeekerId"));
        dto.setResumeId((int) payload.get("resumeId"));
        dto.setStatus(ApplicationStatus.APPLIED);
        dto.setAppliedDate(LocalDate.now());

        StringBuilder fullResponse = new StringBuilder();

        // ✅ Cover Letter First
        if (payload.containsKey("coverLetter")) {
            fullResponse.append("Cover Letter: ")
                .append(payload.get("coverLetter").toString())
                .append("\n\n");
        }

        // ✅ Pretty Labels for Screening Questions
        Map<String, String> readableLabels = Map.of(
            "ugPercentage", "UG %",
            "pgPercentage", "PG %",
            "gradYear", "Graduation Year",
            "priorExperience", "Experience",
            "recentProject", "Project",
            "relocation", "Relocation",
            "noticePeriod", "Notice Period",
            "portfolio", "Portfolio",
            "workAuth", "Work Auth",
            "nightShift", "Night Shift"
        );

        if (payload.containsKey("answers")) {
            Map<String, String> answers = (Map<String, String>) payload.get("answers");
            for (Map.Entry<String, String> entry : answers.entrySet()) {
                String label = readableLabels.getOrDefault(entry.getKey(), entry.getKey());
                String value = (entry.getValue() == null || entry.getValue().trim().isEmpty()) ? "-" : entry.getValue();
                fullResponse.append(label).append(": ").append(value).append("\n");
            }
        }

        dto.setCoverLetter(fullResponse.toString());

        return ResponseEntity.ok(applicationsService.saveApplication(dto));
    }


    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ApplicationsDto>> getAll() {
        return ResponseEntity.ok(applicationsService.getAllApplications());
    }

    
    @PreAuthorize("hasAnyRole('EMPLOYER', 'JOB_SEEKER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationsDto> getById(@PathVariable int id) {
        ApplicationsDto dto = applicationsService.getApplicationById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // Seeker updating his application
    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApplicationsDto> update(@PathVariable int id, @RequestBody ApplicationsDto dto) {
        ApplicationsDto updated = applicationsService.updateApplication(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
    

    @PreAuthorize("hasRole('JOB_SEEKER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        applicationsService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
    
    
    
    @PreAuthorize("hasAnyRole('EMPLOYER', 'JOB_SEEKER')")
    @GetMapping("/jobseeker/{seekerId}")
    public ResponseEntity<List<ApplicationsDto>> getByJobSeeker(@PathVariable int seekerId) {
        return ResponseEntity.ok(applicationsService.getApplicationsByJobSeekerId(seekerId));
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationsDto>> getByJob(@PathVariable int jobId) {
        return ResponseEntity.ok(applicationsService.getApplicationsByJobId(jobId));
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/employer/{employerId}")
    public ResponseEntity<List<ApplicationsDto>> getByEmployer(@PathVariable int employerId) {
        return ResponseEntity.ok(applicationsService.getApplicationsByEmployerId(employerId));
    }

    //Employer updates the status of application like 'rejected, hired'
    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationsDto> updateStatus(
            @PathVariable int applicationId,
            @RequestParam String status) {
        ApplicationsDto updated = applicationsService.updateApplicationStatus(applicationId, status);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
    
    

}
