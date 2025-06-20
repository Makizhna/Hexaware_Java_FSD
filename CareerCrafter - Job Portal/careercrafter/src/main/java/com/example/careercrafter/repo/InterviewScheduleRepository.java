package com.example.careercrafter.repo;

import com.example.careercrafter.entity.InterviewSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule, Integer> {
    
}
