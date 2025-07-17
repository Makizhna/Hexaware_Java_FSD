package com.example.carrercrafter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carrercrafter.entities.InterviewSchedule;

public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule, Integer> {
	
	void deleteByApplication_Id(int id);
	boolean existsByApplication_Id(int applicationId);
	List<InterviewSchedule> findByApplication_JobSeeker_SeekerId(int seekerId);
	Optional<InterviewSchedule> findByApplication_Id(int applicationId);





}
