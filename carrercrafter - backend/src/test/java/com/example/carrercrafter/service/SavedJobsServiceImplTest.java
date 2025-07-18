package com.example.carrercrafter.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.Mock;

import com.example.carrercrafter.ServiceImpl.SavedJobsServiceImpl;
import com.example.carrercrafter.dto.SavedJobsDto;
import com.example.carrercrafter.entities.*;
import com.example.carrercrafter.repository.*;

import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
public class SavedJobsServiceImplTest {
	
	

	    @InjectMocks
	    private SavedJobsServiceImpl service;

	    @Mock
	    private SavedJobsRepository savedJobsRepo;

	    @Mock
	    private JobSeekerRepository jobSeekerRepo;

	    @Mock
	    private JobsRepository jobsRepo;

	    @Mock
	    private EntityManager entityManager;

	    private JobSeeker seeker;
	    private Jobs job;
	    private SavedJobs saved;
	    private SavedJobsDto dto;

	    @BeforeEach
	    void setup() {
	        seeker = new JobSeeker();
	        seeker.setSeekerId(10);
	        seeker.setName("Jaga");

	        Employer employer = new Employer();
	        employer.setCompanyName("Tech Corp");
	        job = new Jobs();
	        job.setJobId(5);
	        job.setEmployer(employer);
	        job.setApplicationDeadline(LocalDate.now().plusDays(10));

	        saved = new SavedJobs();
	        saved.setId(1);
	        saved.setJob(job);
	        saved.setJobSeeker(seeker);
	        saved.setSavedDate(LocalDate.now());

	        dto = new SavedJobsDto();
	        dto.setId(1);
	        dto.setJobId(5);
	        dto.setJobSeekerId(10);
	        dto.setSavedDate(LocalDate.now());
	    }
	    
	    
	    @Test
	    void save_ShouldSaveSuccessfully_WhenNotDuplicate() {
	        when(jobSeekerRepo.findById(10)).thenReturn(Optional.of(seeker));
	        when(jobsRepo.findById(5)).thenReturn(Optional.of(job));
	        when(savedJobsRepo.existsByJobAndJobSeeker(job, seeker)).thenReturn(false);
	        when(savedJobsRepo.save(any())).thenAnswer(inv -> {
	            SavedJobs entity = inv.getArgument(0);
	            entity.setId(100);
	            return entity;
	        });

	        SavedJobsDto result = service.save(dto);

	        assertNotNull(result);
	        assertEquals(5, result.getJobId());
	        verify(entityManager).flush();
	    }

	    
	    @Test
	    void save_ShouldThrow_WhenAlreadySaved() {
	        when(jobSeekerRepo.findById(10)).thenReturn(Optional.of(seeker));
	        when(jobsRepo.findById(5)).thenReturn(Optional.of(job));
	        when(savedJobsRepo.existsByJobAndJobSeeker(job, seeker)).thenReturn(true);

	        assertThrows(RuntimeException.class, () -> service.save(dto));
	    }

	    
	    
	    @Test
	    void save_ShouldThrow_WhenSeekerNotFound() {
	        when(jobSeekerRepo.findById(10)).thenReturn(Optional.empty());

	        assertThrows(RuntimeException.class, () -> service.save(dto));
	    }

	    
	    
	    @Test
	    void getAll_ShouldReturnList() {
	        List<SavedJobs> list = List.of(saved);
	        when(savedJobsRepo.findAll()).thenReturn(list);

	        List<SavedJobsDto> result = service.getAll();

	        assertEquals(1, result.size());
	        assertEquals("Tech Corp", result.get(0).getCompanyName());
	    }

	    
	    
	    @Test
	    void getById_ShouldReturnDto_IfExists() {
	        when(savedJobsRepo.findById(1)).thenReturn(Optional.of(saved));

	        SavedJobsDto result = service.getById(1);

	        assertEquals(5, result.getJobId());
	        assertEquals(10, result.getJobSeekerId());
	    }

	    
	    
	    @Test
	    void getById_ShouldReturnNull_IfNotFound() {
	        when(savedJobsRepo.findById(1)).thenReturn(Optional.empty());

	        SavedJobsDto result = service.getById(1);
	        assertNull(result);
	    }

	    
	    
	    
	    @Test
	    void updateSavedJob_ShouldUpdate_WhenFound() {
	        when(savedJobsRepo.findById(1)).thenReturn(Optional.of(saved));
	        when(jobSeekerRepo.findById(10)).thenReturn(Optional.of(seeker));
	        when(jobsRepo.findById(5)).thenReturn(Optional.of(job));
	        when(savedJobsRepo.save(any())).thenReturn(saved);

	        SavedJobsDto result = service.updateSavedJob(1, dto);

	        assertNotNull(result);
	        assertEquals("Tech Corp", result.getCompanyName());
	    }

	    
	    
	    
	    @Test
	    void updateSavedJob_ShouldReturnNull_IfNotFound() {
	        when(savedJobsRepo.findById(1)).thenReturn(Optional.empty());

	        SavedJobsDto result = service.updateSavedJob(1, dto);

	        assertNull(result);
	    }

	    
	    
	    @Test
	    void delete_ShouldCallRepo() {
	        service.delete(1);
	        verify(savedJobsRepo).deleteById(1);
	    }

	    
	    
	    
	    @Test
	    void getByJobSeeker_ShouldReturnSavedJobs() {
	        List<SavedJobs> savedList = List.of(saved);
	        when(savedJobsRepo.findByJobSeeker_SeekerId(10)).thenReturn(savedList);

	        List<SavedJobsDto> result = service.getByJobSeeker(10);

	        assertEquals(1, result.size());
	        assertEquals(5, result.get(0).getJobId());
	    }
	
}



