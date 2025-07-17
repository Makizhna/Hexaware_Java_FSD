package com.example.carrercrafter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.carrercrafter.entities.JobSeeker;
import com.example.carrercrafter.entities.Jobs;
import com.example.carrercrafter.entities.SavedJobs;

public interface SavedJobsRepository extends JpaRepository<SavedJobs, Integer> {
	
	void deleteByJobSeeker_SeekerId(int seekerId);
	void deleteByJob_JobId(int jobId);
	
	
	
	List<SavedJobs> findByJobSeeker_SeekerId(@Param("seekerId") int seekerId);
	boolean existsByJobAndJobSeeker(Jobs job, JobSeeker jobSeeker);




	
}
