package com.example.careercrafter.repo;

import com.example.careercrafter.entity.SavedJobs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedJobsRepository extends JpaRepository<SavedJobs, Integer> {
    
}
