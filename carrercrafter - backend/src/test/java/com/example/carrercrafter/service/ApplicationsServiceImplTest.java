package com.example.carrercrafter.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.example.carrercrafter.ServiceImpl.*;
import com.example.carrercrafter.entities.*;
import com.example.carrercrafter.repository.*;

class ApplicationsServiceImplTest {

    @InjectMocks
    private ApplicationsServiceImpl applicationsService;

    @Mock
    private ApplicationsRepository applicationsRepo;

    @Mock
    private JobSeekerRepository jobSeekerRepo;

    @Mock
    private JobsRepository jobsRepo;

    @Mock
    private JobSeekerResumeRepository resumeRepo;

    @Mock
    private InterviewScheduleRepository interviewScheduleRepo;

    private JobSeeker mockSeeker;
    private Jobs mockJob;
    private JobSeekerResume mockResume;

    @BeforeEach
    void setUp() {
        mockSeeker = new JobSeeker();
        mockSeeker.setSeekerId(1);

        mockJob = new Jobs();
        mockJob.setJobId(10);
        Employer employer = new Employer();
        employer.setActive(true);
        mockJob.setEmployer(employer);

        mockResume = new JobSeekerResume();
        mockResume.setResumeId(5);
    }
}

