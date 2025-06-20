package com.example.careercrafter.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.careercrafter.entity.Applications;
import com.example.careercrafter.entity.InterviewSchedule;
import com.example.careercrafter.repo.ApplicationRepository;
import com.example.careercrafter.repo.InterviewScheduleRepository;

@RestController
@RequestMapping("/api/interviews")
public class InterviewScheduleApiController {

    @Autowired
    private InterviewScheduleRepository interviewRepo;

    @Autowired
    private ApplicationRepository appRepo;

    @PostMapping
    public ResponseEntity<?> schedule(@RequestBody InterviewSchedule schedule) {
        if (schedule.getApplication() == null || schedule.getApplication().getId() == 0) {
            return new ResponseEntity<>("Application ID required", HttpStatus.BAD_REQUEST);
        }

        Optional<Applications> app = appRepo.findById(schedule.getApplication().getId());
        if (app.isEmpty()) return new ResponseEntity<>("Application not found", HttpStatus.NOT_FOUND);

        schedule.setApplication(app.get());
        return new ResponseEntity<>(interviewRepo.save(schedule), HttpStatus.CREATED);
    }

    @GetMapping
    public List<InterviewSchedule> getAll() {
        return interviewRepo.findAll();
    }
}
