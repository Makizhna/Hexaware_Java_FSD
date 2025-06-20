package com.example.careercrafter.repo;

import com.example.careercrafter.entity.Employers;
import com.example.careercrafter.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobsRepository extends JpaRepository<Jobs, Integer> {
	void deleteByPostedBy(Employers employer);

    
}
