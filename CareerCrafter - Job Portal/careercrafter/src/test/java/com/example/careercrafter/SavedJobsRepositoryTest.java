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
public class SavedJobsRepositoryTest {
    @Autowired
    private SavedJobsRepository savedJobRepo;

    @Autowired
    private JobsRepository jobRepo;

    @Autowired
    private JobSeekerRepository jobSeekerRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmployerRepository employerRepo;

    private SavedJobs savedJob;

    @BeforeEach
    public void setUp() {
        Users empUser = userRepo.save(new Users("saveemp@example.com", "pass", "employer"));
        Employers emp = employerRepo.save(new Employers("Rekha", "Manager", "Techie Ltd", "Pune", empUser));
        Jobs job = jobRepo.save(new Jobs("Full Stack Dev", "MERN Stack", "Remote", emp));

        Users seekerUser = userRepo.save(new Users("saver@example.com", "pass", "job_seeker"));
        JobSeekers seeker = jobSeekerRepo.save(new JobSeekers("Maya", "MSC IT", "Node.js", "Fresher", "9871234567", "resume_maya.pdf", LocalDate.of(2000, 4, 2), seekerUser));

        savedJob = savedJobRepo.save(new SavedJobs(seeker, job));
    }

    @Test
    public void testFindSavedJob() {
        SavedJobs found = savedJobRepo.findById(savedJob.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(savedJob.getJob().getTitle(), found.getJob().getTitle());
    }
}
