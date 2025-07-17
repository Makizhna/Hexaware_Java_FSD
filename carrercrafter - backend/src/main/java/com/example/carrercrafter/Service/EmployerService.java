package com.example.carrercrafter.Service;

import java.util.List;

import com.example.carrercrafter.dto.EmployerDto;


public interface EmployerService {
	
	EmployerDto saveEmployer(EmployerDto employerDto);
	List<EmployerDto> getAllEmployers();
	EmployerDto getEmployerById(int id);
	EmployerDto updateEmployer(int id, EmployerDto employerDto);
	void deleteEmployer(int id);
	List<EmployerDto> searchByLocation(String location);
	EmployerDto getByUserId(int userId);




}



