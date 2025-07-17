package com.example.carrercrafter.service;

import com.example.carrercrafter.ServiceImpl.JobsServiceImpl;
import com.example.carrercrafter.dto.EmployerDto;
import com.example.carrercrafter.dto.JobsDto;
import com.example.carrercrafter.entities.*;
import com.example.carrercrafter.enums.JobType;
import com.example.carrercrafter.enums.LocationType;
import com.example.carrercrafter.mapper.JobsMapper;
import com.example.carrercrafter.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobsServiceImplTest {

    @InjectMocks
    private JobsServiceImpl jobsService;

    @Mock
    private JobsRepository jobsRepo;

    @Mock
    private EmployerRepository employerRepo;

    @Mock
    private ApplicationsRepository applicationRepo;

    @Mock
    private InterviewScheduleRepository interviewScheduleRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveJob() {
        Employer emp = new Employer();
        emp.setEmployeeId(1);
        emp.setActive(true);

        JobsDto dto = new JobsDto();
        dto.setTitle("Java Developer");
        dto.setEmployer(new EmployerDto());
        dto.getEmployer().setEmployeeId(1);

        Jobs job = JobsMapper.mapToJobs(dto, emp);

        when(employerRepo.findById(1)).thenReturn(Optional.of(emp));
        when(jobsRepo.save(any(Jobs.class))).thenReturn(job);

        JobsDto result = jobsService.saveJob(dto);

        assertEquals("Java Developer", result.getTitle());
        verify(jobsRepo, times(1)).save(any());
    }

    @Test
    void testGetAllJobs() {
        Employer emp = new Employer();
        emp.setActive(true);
        emp.setEmployeeId(1);

        Jobs job = new Jobs();
        job.setTitle("Backend Engineer");
        job.setEmployer(emp);

        when(jobsRepo.findAll()).thenReturn(List.of(job));

        List<JobsDto> result = jobsService.getAllJobs();

        assertEquals(1, result.size());
        assertEquals("Backend Engineer", result.get(0).getTitle());
    }

    @Test
    void testGetJobById() {
        Jobs job = new Jobs();
        job.setJobId(1);
        job.setTitle("React Developer");

        when(jobsRepo.findById(1)).thenReturn(Optional.of(job));

        JobsDto result = jobsService.getJobById(1);

        assertEquals("React Developer", result.getTitle());
    }

    @Test
    void testGetJobsByEmployer() {
        Jobs job = new Jobs();
        job.setTitle("HR Analyst");

        when(jobsRepo.findByEmployer_EmployeeId(2)).thenReturn(List.of(job));

        List<JobsDto> result = jobsService.getJobsByEmployer(2);

        assertEquals(1, result.size());
        assertEquals("HR Analyst", result.get(0).getTitle());
    }

    @Test
    void testUpdateJob() {
        int jobId = 1;
        Jobs existing = new Jobs();
        existing.setJobId(jobId);

        JobsDto updateDto = new JobsDto();
        updateDto.setTitle("Updated Title");
        updateDto.setDescription("Updated Description");
        updateDto.setEmployer(new EmployerDto());
        updateDto.getEmployer().setEmployeeId(1);
        updateDto.setActive(true);

        when(jobsRepo.findById(jobId)).thenReturn(Optional.of(existing));
        when(employerRepo.findById(1)).thenReturn(Optional.of(new Employer()));
        when(jobsRepo.save(any(Jobs.class))).thenAnswer(i -> i.getArgument(0));

        JobsDto result = jobsService.updateJob(jobId, updateDto);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
    }

    @Test
    void testDeleteJob() {
        int jobId = 1;

        Applications app1 = new Applications();
        app1.setId(101);
        Applications app2 = new Applications();
        app2.setId(102);

        when(applicationRepo.existsByJob_JobId(jobId)).thenReturn(true);
        when(applicationRepo.findByJob_JobId(jobId)).thenReturn(List.of(app1, app2));

        jobsService.deleteJob(jobId);

        verify(interviewScheduleRepo, times(2)).deleteByApplication_Id(anyInt());
        verify(applicationRepo).deleteAll(anyList());
        verify(jobsRepo).deleteById(jobId);
    }
}
