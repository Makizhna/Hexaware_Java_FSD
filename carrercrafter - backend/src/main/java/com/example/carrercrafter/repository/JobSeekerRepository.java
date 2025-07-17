package com.example.carrercrafter.repository;
import com.example.carrercrafter.entities.JobSeeker;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Integer> {

	
	boolean existsByUser_Id(int userId);
	JobSeeker findByUser_Id(int userId);
	List<JobSeeker> findBySkillsContainingIgnoreCase(String skill);
	List<JobSeeker> findBySkillsContainingIgnoreCaseAndExperienceContainingIgnoreCase(String skills, String experience);


}
