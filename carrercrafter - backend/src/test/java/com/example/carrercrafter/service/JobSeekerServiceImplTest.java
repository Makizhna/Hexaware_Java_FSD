package com.example.carrercrafter.service;

import com.example.carrercrafter.ServiceImpl.JobSeekerServiceImpl;
import com.example.carrercrafter.dto.JobSeekerDto;
import com.example.carrercrafter.dto.UserDto;
import com.example.carrercrafter.entities.*;
import com.example.carrercrafter.mapper.JobSeekerMapper;
import com.example.carrercrafter.mapper.UserMapper;
import com.example.carrercrafter.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobSeekerServiceImplTest {

    @InjectMocks
    private JobSeekerServiceImpl jobSeekerService;

    @Mock
    private JobSeekerRepository jobSeekerRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private ApplicationsRepository applicationsRepo;

    @Mock
    private InterviewScheduleRepository interviewScheduleRepo;

    @Mock
    private SavedJobsRepository savedJobsRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveJobSeeker() {
        JobSeekerDto dto = new JobSeekerDto();
        dto.setName("John");
        dto.setEducation("B.Tech");
        dto.setSkills("Java");
        dto.setExperience("2 years");
        dto.setDob(LocalDate.of(1995, 5, 10));
        dto.setLocation("Chennai");

        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setEmail("john@example.com");
        dto.setUser(userDto);

        User user = UserMapper.mapToUser(userDto);
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        JobSeeker savedEntity = JobSeekerMapper.toEntity(dto);
        when(jobSeekerRepo.save(any(JobSeeker.class))).thenReturn(savedEntity);

        JobSeekerDto result = jobSeekerService.saveJobSeeker(dto);
        assertEquals("John", result.getName());
        verify(jobSeekerRepo).save(any(JobSeeker.class));
    }

    @Test
    void testGetAllJobSeekers() {
        List<JobSeeker> seekers = Arrays.asList(new JobSeeker(), new JobSeeker());
        when(jobSeekerRepo.findAll()).thenReturn(seekers);
        List<JobSeekerDto> result = jobSeekerService.getAllJobSeekers();
        assertEquals(2, result.size());
    }

    @Test
    void testGetJobSeekerById() {
        JobSeeker seeker = new JobSeeker();
        seeker.setSeekerId(1);
        seeker.setName("Alice");

        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(seeker));
        JobSeekerDto result = jobSeekerService.getJobSeekerById(1);
        assertEquals("Alice", result.getName());
    }

    @Test
    void testUpdateJobSeeker() {
        JobSeeker existing = new JobSeeker();
        existing.setSeekerId(1);
        existing.setName("Old Name");

        User user = new User();
        user.setId(1);
        user.setEmail("old@example.com");
        existing.setUser(user);

        JobSeekerDto dto = new JobSeekerDto();
        dto.setName("New Name");
        dto.setEducation("MBA");
        dto.setSkills("React");
        dto.setExperience("3 years");
        dto.setPhone("1234567890");
        dto.setLocation("Delhi");
        dto.setDob(LocalDate.of(1998, 1, 1));
        dto.setUser(UserMapper.mapToUserDto(user));

        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(existing));
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(jobSeekerRepo.save(any(JobSeeker.class))).thenAnswer(i -> i.getArgument(0));

        JobSeekerDto result = jobSeekerService.updateJobSeeker(1, dto);
        assertEquals("New Name", result.getName());
    }

    @Test
    void testGetByUserId() {
        JobSeeker seeker = new JobSeeker();
        seeker.setSeekerId(1);
        when(jobSeekerRepo.findByUser_Id(5)).thenReturn(seeker);

        JobSeekerDto result = jobSeekerService.getByUserId(5);
        assertNotNull(result);
        assertEquals(1, result.getSeekerId());
    }

    @Test
    void testSearchBySkill() {
        List<JobSeeker> seekers = List.of(new JobSeeker(), new JobSeeker());
        when(jobSeekerRepo.findBySkillsContainingIgnoreCase("java")).thenReturn(seekers);

        List<JobSeekerDto> result = jobSeekerService.searchBySkill("java");
        assertEquals(2, result.size());
    }

    @Test
    void testSearchBySkillsAndExperience() {
        List<JobSeeker> seekers = List.of(new JobSeeker());
        when(jobSeekerRepo.findBySkillsContainingIgnoreCaseAndExperienceContainingIgnoreCase("java", "2"))
                .thenReturn(seekers);

        List<JobSeekerDto> result = jobSeekerService.searchBySkillsAndExperience("java", "2");
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteJobSeeker() {
        JobSeeker seeker = new JobSeeker();
        seeker.setSeekerId(1);
        User user = new User();
        user.setId(2);
        seeker.setUser(user);

        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(seeker));
        when(applicationsRepo.findByJobSeeker_SeekerId(1)).thenReturn(new ArrayList<>());

        jobSeekerService.deleteJobSeeker(1);

        verify(applicationsRepo).deleteAll(any());
        verify(savedJobsRepo).deleteByJobSeeker_SeekerId(1);
        verify(jobSeekerRepo).deleteById(1);
        verify(userRepo).deleteById(2);
    }
}
