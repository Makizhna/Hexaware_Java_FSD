package com.example.carrercrafter.repository;

import com.example.carrercrafter.entities.JobSeekerResume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerResumeRepository extends JpaRepository<JobSeekerResume, Integer> {
    JobSeekerResume findByJobSeeker_SeekerId(int seekerId);
}
