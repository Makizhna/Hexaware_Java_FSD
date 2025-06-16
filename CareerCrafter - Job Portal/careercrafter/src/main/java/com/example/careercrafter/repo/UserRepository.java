package com.example.careercrafter.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.careercrafter.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {
	
	Users findByEmailAndPassword(String email, String password);
}
