package com.example.careercrafter;

import com.example.careercrafter.entity.*;
import com.example.careercrafter.repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ApplicationRepositoryTest {
    @Autowired
    private ApplicationRepository appRepo;

    @Autowired
    private JobsRepository jobRepo;

    @Autowired
    private JobSeekerRepository jobSeekerRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmployerRepository employerRepo;

    private Applications app;

    @BeforeEach
    public void setUp() {
        Users empUser = userRepo.save(new Users("empapply@example.com", "pass", "employer"));
        Employers emp = employerRepo.save(new Employers("Neha", "Manager", "Techy", "Mumbai", empUser));
        Jobs job = jobRepo.save(new Jobs("React Dev", "Build UIs", "Bangalore", emp));

        Users seekerUser = userRepo.save(new Users("seekerapply@example.com", "pass", "job_seeker"));
        JobSeekers seeker = jobSeekerRepo.save(new JobSeekers("Pooja", "MCA", "React", "1 year", "9123456789", "resume.pdf", LocalDate.of(1998, 6, 10), seekerUser));

        app = new Applications(job, seeker, "resume.pdf", "applied");
        appRepo.save(app);
    }

    @Test
    public void testFindApplication() {
        Applications found = appRepo.findById(app.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("applied", found.getStatus());
    }
}