package com.example.carrercrafter.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carrercrafter.entities.Employer;


public interface EmployerRepository extends JpaRepository<Employer, Integer>{

	boolean existsByUser_Id(int userId);
	Employer findByUser_Id(int userId);
	List<Employer> findByLocationContainingIgnoreCase(String location);
	List<Employer> findByActiveTrue();




}
