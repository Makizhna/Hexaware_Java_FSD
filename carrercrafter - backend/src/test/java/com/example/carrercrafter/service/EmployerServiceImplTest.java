package com.example.carrercrafter.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.example.carrercrafter.ServiceImpl.EmployerServiceImpl;
import com.example.carrercrafter.dto.EmployerDto;
import com.example.carrercrafter.entities.Applications;
import com.example.carrercrafter.entities.Employer;
import com.example.carrercrafter.entities.Jobs;
import com.example.carrercrafter.entities.User;
import com.example.carrercrafter.enums.UserRole;
import com.example.carrercrafter.mapper.EmployerMapper;
import com.example.carrercrafter.repository.*;

public class EmployerServiceImplTest {

    @InjectMocks
    private EmployerServiceImpl employerService;

    @Mock
    private EmployerRepository employerRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private JobsRepository jobsRepo;
    @Mock
    private ApplicationsRepository applicationRepo;
    @Mock
    private InterviewScheduleRepository interviewScheduleRepo;
    @Mock
    private SavedJobsRepository savedJobsRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test saveEmployer()
    @Test
    void testSaveEmployer() {
        EmployerDto dto = new EmployerDto();
        dto.setName("HR");
        dto.setCompanyName("Infosys");

        User user = new User();
        user.setId(1);
        user.setRole(UserRole.EMPLOYER);

        dto.setUser(new com.example.carrercrafter.dto.UserDto());
        dto.getUser().setId(1);

        Employer emp = EmployerMapper.toEntity(dto, user);
        emp.setActive(true);

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(employerRepo.save(any(Employer.class))).thenReturn(emp);

        EmployerDto result = employerService.saveEmployer(dto);

        assertEquals("HR", result.getName());
        assertTrue(result.isActive());
        verify(employerRepo).save(any(Employer.class));
    }

    // Test getAllEmployers()
    @Test
    void testGetAllEmployers() {
        Employer emp1 = new Employer();
        emp1.setName("Emp1");
        emp1.setActive(true);

        Employer emp2 = new Employer();
        emp2.setName("Emp2");
        emp2.setActive(true);

        when(employerRepo.findByActiveTrue()).thenReturn(List.of(emp1, emp2));
        List<EmployerDto> result = employerService.getAllEmployers();
        assertEquals(2, result.size());
    }

    // Test getEmployerById()
    @Test
    void testGetEmployerById() {
        Employer emp = new Employer();
        emp.setEmployeeId(1);
        emp.setName("Test Emp");

        when(employerRepo.findById(1)).thenReturn(Optional.of(emp));
        EmployerDto result = employerService.getEmployerById(1);
        assertEquals("Test Emp", result.getName());
    }

    // Test updateEmployer()
    @Test
    void testUpdateEmployer() {
        Employer existing = new Employer();
        existing.setEmployeeId(1);
        existing.setName("Old Name");

        User user = new User();
        user.setId(1);

        EmployerDto dto = new EmployerDto();
        dto.setName("New Name");
        dto.setUser(new com.example.carrercrafter.dto.UserDto());
        dto.getUser().setId(1);

        when(employerRepo.findById(1)).thenReturn(Optional.of(existing));
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(employerRepo.save(any(Employer.class))).thenAnswer(i -> i.getArgument(0));

        EmployerDto result = employerService.updateEmployer(1, dto);
        assertEquals("New Name", result.getName());
    }

    // Test getByUserId()
    @Test
    void testGetByUserId() {
        Employer emp = new Employer();
        emp.setName("Linked User");

        when(employerRepo.findByUser_Id(1)).thenReturn(emp);
        EmployerDto result = employerService.getByUserId(1);
        assertEquals("Linked User", result.getName());
    }

    // Test searchByLocation()
    @Test
    void testSearchByLocation() {
        Employer emp = new Employer();
        emp.setLocation("Bangalore");

        when(employerRepo.findByLocationContainingIgnoreCase("bangalore")).thenReturn(List.of(emp));
        List<EmployerDto> result = employerService.searchByLocation("bangalore");
        assertEquals(1, result.size());
        assertEquals("Bangalore", result.get(0).getLocation());
    }
    
    
    @Test
    void testDeleteEmployer() {
        int empId = 5;

        Employer emp = new Employer();
        emp.setEmployeeId(empId);
        emp.setActive(true);

        User user = new User();
        user.setId(10);
        emp.setUser(user);

        // Set up mock job
        Jobs job = new Jobs();
        job.setJobId(100);
        job.setActive(true);
        job.setEmployer(emp);

        Applications app = new Applications();
        app.setId(200);
        app.setJob(job);

        when(employerRepo.findById(empId)).thenReturn(Optional.of(emp));
        when(jobsRepo.findByEmployer_EmployeeId(empId)).thenReturn(List.of(job));
        when(applicationRepo.findByJob_JobId(100)).thenReturn(List.of(app));

        // Act
        employerService.deleteEmployer(empId);

        // Assert soft delete behavior
        assertFalse(emp.isActive());
        verify(employerRepo).save(emp); // soft delete saved
        verify(interviewScheduleRepo).deleteByApplication_Id(200);
        verify(applicationRepo).deleteAll(any());
        verify(savedJobsRepo).deleteByJob_JobId(100);
        verify(jobsRepo).save(any()); // soft delete jobs
        verify(userRepo).deleteById(10); // final user delete
    }

}
