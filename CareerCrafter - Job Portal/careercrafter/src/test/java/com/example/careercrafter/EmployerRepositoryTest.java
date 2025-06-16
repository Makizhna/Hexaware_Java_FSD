package com.example.careercrafter;

import com.example.careercrafter.entity.Employers;
import com.example.careercrafter.entity.Users;
import com.example.careercrafter.repo.EmployerRepository;
import com.example.careercrafter.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EmployerRepositoryTest {
    @Autowired
    private EmployerRepository employerRepo;

    @Autowired
    private UserRepository userRepo;

    private Employers employer;

    @BeforeEach
    public void setUp() {
        Users user = new Users("employer@example.com", "emp123", "employer");
        user = userRepo.save(user);

        employer = new Employers("Ravi", "HR Manager", "TechCorp", "Chennai", user);
        employerRepo.save(employer);
    }

    @Test
    public void testFindEmployer() {
        Employers found = employerRepo.findById(employer.getUserId()).orElse(null);
        assertNotNull(found);
        assertEquals("Ravi", found.getName());
    }
}
