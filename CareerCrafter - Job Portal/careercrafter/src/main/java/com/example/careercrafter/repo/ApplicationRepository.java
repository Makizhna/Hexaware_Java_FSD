package com.example.careercrafter.repo;

import com.example.careercrafter.entity.Applications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Applications, Integer> {
    
}
