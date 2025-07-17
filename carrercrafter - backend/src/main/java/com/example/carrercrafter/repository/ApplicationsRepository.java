package com.example.carrercrafter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carrercrafter.entities.Applications;

public interface ApplicationsRepository extends JpaRepository<Applications, Integer> {
	
	List<Applications> findByJob_JobId(int jobId);
	boolean existsByJob_JobId(int jobId);
	List<Applications> findByJobSeeker_SeekerId(int seekerId); 
	List<Applications> findByJob_Employer_EmployeeId(int employeeId); 
	boolean existsByJob_JobIdAndJobSeeker_SeekerId(int jobId, int seekerId);


	


}