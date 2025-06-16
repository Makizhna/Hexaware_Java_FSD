package com.example.careercrafter;

import com.example.careercrafter.entity.JobSeekers;
import com.example.careercrafter.entity.Users;
import com.example.careercrafter.repo.JobSeekerRepository;
import com.example.careercrafter.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class JobSeekerRepositoryTest {
    @Autowired
    private JobSeekerRepository jobSeekerRepo;

    @Autowired
    private UserRepository userRepo;

    private JobSeekers jobSeeker;

    @BeforeEach
    public void setUp() {
        Users user = new Users("jobseeker@example.com", "pass123", "job_seeker");
        user = userRepo.save(user);

        jobSeeker = new JobSeekers("Karthik", "B.Tech", "Java", "2 years", "9876543210", "resume.pdf", LocalDate.of(1995, 1, 1), user);
        jobSeekerRepo.save(jobSeeker);
    }

    @Test
    public void testFindJobSeeker() {
        JobSeekers found = jobSeekerRepo.findById(jobSeeker.getUserId()).orElse(null);
        assertNotNull(found);
        assertEquals("Karthik", found.getName());
    }
}
