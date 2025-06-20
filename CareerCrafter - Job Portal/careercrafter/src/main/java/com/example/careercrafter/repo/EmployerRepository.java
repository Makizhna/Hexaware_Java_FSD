package com.example.careercrafter.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.careercrafter.entity.Employers;

public interface EmployerRepository extends JpaRepository<Employers, Integer> {
	boolean existsByUser_Id(int userId);
	Employers findByUser_Id(int userId);
	void deleteByUser_Id(int userId);

}
