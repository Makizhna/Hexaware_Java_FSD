package com.example.careercrafter.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.careercrafter.entity.JobSeekers;

public interface JobSeekerRepository extends JpaRepository<JobSeekers, Integer> {
	boolean existsByUser_Id(int userId);
	void deleteByUser_Id(int userId);
}