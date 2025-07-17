package com.example.carrercrafter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.carrercrafter.ServiceImpl.UserServiceImpl;
import com.example.carrercrafter.dto.UserDto;
import com.example.carrercrafter.entities.Employer;
import com.example.carrercrafter.entities.JobSeeker;
import com.example.carrercrafter.entities.User;
import com.example.carrercrafter.enums.UserRole;
import com.example.carrercrafter.mapper.UserMapper;
import com.example.carrercrafter.repository.ApplicationsRepository;
import com.example.carrercrafter.repository.EmployerRepository;
import com.example.carrercrafter.repository.InterviewScheduleRepository;
import com.example.carrercrafter.repository.JobSeekerRepository;
import com.example.carrercrafter.repository.JobSeekerResumeRepository;
import com.example.carrercrafter.repository.JobsRepository;
import com.example.carrercrafter.repository.SavedJobsRepository;
import com.example.carrercrafter.repository.UserRepository;

public class UserServiceImplTest {


    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepo;
    @Mock
    private JobSeekerRepository jobSeekerRepo;
    @Mock
    private EmployerRepository employerRepo;
    @Mock
    private ApplicationsRepository applicationsRepo;
    @Mock
    private InterviewScheduleRepository interviewScheduleRepo;
    @Mock
    private SavedJobsRepository savedJobsRepo;
    @Mock
    private JobSeekerResumeRepository resumeRepository;
    @Mock
    private JobsRepository jobsRepo;
    
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test saveUser()
    @Test
    void testSaveUser() {
        UserDto dto = new UserDto();
        dto.setName("John");
        dto.setEmail("john@example.com");
        dto.setPassword("pass");

        User user = UserMapper.mapToUser(dto);
        user.setPassword("encoded");

        when(passwordEncoder.encode("pass")).thenReturn("encoded");
        when(userRepo.save(any(User.class))).thenReturn(user);

        UserDto result = userService.saveUser(dto);
        assertEquals("John", result.getName());
        assertEquals("john@example.com", result.getEmail());
        verify(userRepo, times(1)).save(any(User.class));
    }

    // Test getAllUsers()
    @Test
    void testGetAllUsers() {
        List<User> users = List.of(new User(), new User());
        when(userRepo.findAll()).thenReturn(users);
        List<UserDto> result = userService.getAllUsers();
        assertEquals(2, result.size());
    }

    // Test getUserById()
    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1);
        user.setName("Test");

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        UserDto result = userService.getUserById(1);
        assertEquals("Test", result.getName());
    }

    // Test updateUser()
    @Test
    void testUpdateUser() {
        User user = new User();
        user.setId(1);
        user.setName("Old");

        UserDto updatedDto = new UserDto();
        updatedDto.setName("New");
        updatedDto.setEmail("new@example.com");
        updatedDto.setPassword("123");
        updatedDto.setRole(UserRole.JOB_SEEKER);

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserDto result = userService.updateUser(1, updatedDto);
        assertEquals("New", result.getName());
        assertEquals("new@example.com", result.getEmail());
    }

    // Test login()
    @Test
    void testLoginSuccess() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("pass");

        when(userRepo.findByEmailAndPassword("user@example.com", "pass")).thenReturn(user);
        UserDto result = userService.login("user@example.com", "pass");
        assertEquals("user@example.com", result.getEmail());
    }

    // Test deleteUserAndRelated()
    @Test
    void testDeleteUserAndRelated() {
        int userId = 10;
        User user = new User();
        user.setId(userId);

        JobSeeker seeker = new JobSeeker();
        seeker.setSeekerId(1);

        Employer employer = new Employer();
        employer.setEmployeeId(2);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(jobSeekerRepo.existsByUser_Id(userId)).thenReturn(true);
        when(jobSeekerRepo.findByUser_Id(userId)).thenReturn(seeker);
        when(applicationsRepo.findByJobSeeker_SeekerId(1)).thenReturn(new ArrayList<>());
        when(resumeRepository.findByJobSeeker_SeekerId(1)).thenReturn(null);

        when(employerRepo.existsByUser_Id(userId)).thenReturn(true);
        when(employerRepo.findByUser_Id(userId)).thenReturn(employer);
        when(jobsRepo.findByEmployer_EmployeeId(2)).thenReturn(new ArrayList<>());

        boolean result = userService.deleteUserAndRelated(userId);
        assertTrue(result);
        verify(userRepo, times(1)).deleteById(userId);
    }
}

