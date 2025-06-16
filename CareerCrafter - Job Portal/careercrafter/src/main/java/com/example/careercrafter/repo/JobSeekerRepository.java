package com.example.careercrafter.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.careercrafter.entity.JobSeekers;

public interface JobSeekerRepository extends JpaRepository<JobSeekers, Integer> {
}