package com.example.carrercrafter.controller.api;

import com.example.carrercrafter.Service.InterviewScheduleService;
import com.example.carrercrafter.dto.InterviewScheduleDto;
import com.example.carrercrafter.enums.InterviewStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interview")
@CrossOrigin(origins = "http://localhost:3000")
public class InterviewScheduleController {

    @Autowired
    private InterviewScheduleService scheduleService;

    
    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping
    public ResponseEntity<InterviewScheduleDto> create(@RequestBody InterviewScheduleDto dto) {
    	System.out.println(" Interview schedule request by: " + SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok(scheduleService.saveSchedule(dto));
    }

    
    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping
    public ResponseEntity<List<InterviewScheduleDto>> getAll() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    
    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/{id}")
    public ResponseEntity<InterviewScheduleDto> getById(@PathVariable int id) {
        InterviewScheduleDto dto = scheduleService.getScheduleById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    
    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{id}")
    public ResponseEntity<InterviewScheduleDto> update(@PathVariable int id, @RequestBody InterviewScheduleDto dto) {
    	dto.setStatus(InterviewStatus.RESCHEDULED);
        InterviewScheduleDto updated = scheduleService.updateSchedule(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    
    @PreAuthorize("hasRole('EMPLOYER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
    
    
    @PreAuthorize("hasRole('JOB_SEEKER')")
    @GetMapping("/jobseeker/{id}/interviews")
    public ResponseEntity<List<InterviewScheduleDto>> getByJobSeeker(@PathVariable int id) {
    	System.out.println(" Fetching interviews for Job Seeker ID: " + id);
        System.out.println(" Auth: " + SecurityContextHolder.getContext().getAuthentication());
        List<InterviewScheduleDto> dtos = scheduleService.getByJobSeekerId(id);
        return ResponseEntity.ok(dtos);
    }
    
 
    
    /*@PreAuthorize("hasRole('JOB_SEEKER')")
    @PostMapping("/{id}/reschedule-request")
    public ResponseEntity<String> requestReschedule(@PathVariable int id, @RequestParam String message) {
        scheduleService.requestReschedule(id, message);
        return ResponseEntity.ok("Reschedule request submitted.");*/
    
  
    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PostMapping("/{id}/reschedule-request")
    public ResponseEntity<String> requestReschedule(@PathVariable int id, @RequestBody Map<String, String> body) {
        String message = body.get("message");
        scheduleService.requestReschedule(id, message);
        return ResponseEntity.ok("Reschedule request submitted.");
        
    }


    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancelInterview(@PathVariable int id, @RequestBody Map<String, String> body) {
        String reason = body.get("reason");
        scheduleService.cancelInterview(id, reason);
        return ResponseEntity.ok("Interview cancelled.");
    }
    
    
    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{id}/approve-reschedule")
    public ResponseEntity<String> approveReschedule(@PathVariable int id) {
        scheduleService.approveReschedule(id);
        return ResponseEntity.ok("Reschedule request approved.");
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{id}/reject-reschedule")
    public ResponseEntity<String> rejectReschedule(@PathVariable int id) {
        scheduleService.rejectReschedule(id);
        return ResponseEntity.ok("Reschedule request rejected.");
    }


    @PreAuthorize("hasRole('JOB_SEEKER')")
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<InterviewScheduleDto> getByApplicationId(@PathVariable int applicationId) {
        return ResponseEntity.ok(scheduleService.getByApplicationId(applicationId));
    }




}
