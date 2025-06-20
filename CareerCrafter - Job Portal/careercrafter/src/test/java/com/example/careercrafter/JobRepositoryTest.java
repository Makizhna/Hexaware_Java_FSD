package com.example.careercrafter;

import com.example.careercrafter.entity.*;
import com.example.careercrafter.repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class JobRepositoryTest {
    @Autowired
    private JobsRepository jobRepo;

    @Autowired
    private EmployerRepository employerRepo;

    @Autowired
    private UserRepository userRepo;

    private Jobs job;

    @BeforeEach
    public void setUp() {
        Users user = new Users("empjob@example.com", "pass", "employer");
        user = userRepo.save(user);

        Employers emp = new Employers("Neha", "Recruiter", "TechInc", "Delhi", user);
        employerRepo.save(emp);

        job = new Jobs("Java Dev", "Spring Boot dev", "Remote", emp);
        jobRepo.save(job);
    }

    @Test
    public void testFindJob() {
        Jobs found = jobRepo.findById(job.getJobId()).orElse(null);
        assertNotNull(found);
        assertEquals("Java Dev", found.getTitle());
    }
}
