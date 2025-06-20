package com.example.careercrafter;

import com.example.careercrafter.entity.*;
import com.example.careercrafter.repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class InterviewScheduleRepositoryTest {
    @Autowired
    private InterviewScheduleRepository interviewRepo;

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

    private InterviewSchedule interview;

    @BeforeEach
    public void setUp() {
        Users empUser = userRepo.save(new Users("interviewemp@example.com", "pass", "employer"));
        Employers emp = employerRepo.save(new Employers("Shyam", "Lead", "TCS", "Mumbai", empUser));
        Jobs job = jobRepo.save(new Jobs("Backend Dev", "REST API", "Chennai", emp));

        Users seekerUser = userRepo.save(new Users("interviewseeker@example.com", "pass", "job_seeker"));
        JobSeekers seeker = jobSeekerRepo.save(new JobSeekers("Rani", "BCA", "Python", "1.5 yrs", "9900887766", "resumes/rani.pdf", LocalDate.of(1999, 5, 5), seekerUser));

        Applications app = appRepo.save(new Applications(job, seeker, "resumes/rani.pdf", "under review"));
        interview = interviewRepo.save(new InterviewSchedule(app, LocalDateTime.of(2025, 6, 10, 10, 0), "online", "scheduled"));
    }

    @Test
    public void testFindInterviewSchedule() {
        InterviewSchedule found = interviewRepo.findById(interview.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("online", found.getMode());
    }
}
