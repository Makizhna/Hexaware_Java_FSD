package com.example.carrercrafter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carrercrafter.entities.JobSeeker;
import com.example.carrercrafter.entities.Jobs;

public interface JobsRepository extends JpaRepository<Jobs, Integer> {
	
	List<Jobs> findByEmployer_EmployeeId(int employerId);
	void deleteByEmployer_EmployeeId(int employerId);
	
	List<Jobs> findBySkillsRequiredContainingIgnoreCase(String skill);
	


	

	



}
