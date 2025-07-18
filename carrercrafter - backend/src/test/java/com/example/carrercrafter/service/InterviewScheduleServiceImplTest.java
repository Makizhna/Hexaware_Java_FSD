package com.example.carrercrafter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.carrercrafter.ServiceImpl.InterviewScheduleServiceImpl;
import com.example.carrercrafter.dto.InterviewScheduleDto;
import com.example.carrercrafter.entities.*;
import com.example.carrercrafter.enums.*;
import com.example.carrercrafter.repository.*;

@ExtendWith(MockitoExtension.class)
public class InterviewScheduleServiceImplTest {
	
	
	    @InjectMocks
	    private InterviewScheduleServiceImpl interviewService;

	    @Mock
	    private InterviewScheduleRepository interviewRepo;

	    @Mock
	    private ApplicationsRepository applicationsRepo;

	    private Applications mockApp;
	    private InterviewSchedule mockSchedule;

	    @BeforeEach
	    void setup() {
	        mockApp = new Applications();
	        mockApp.setId(1);
	        JobSeeker seeker = new JobSeeker();
	        seeker.setName("Jaga");
	        seeker.setSeekerId(22);
	        Jobs job = new Jobs();
	        job.setTitle("Software Engineer");
	        Employer employer = new Employer();
	        employer.setCompanyName("Tech Corp");
	        job.setEmployer(employer);
	        mockApp.setJobSeeker(seeker);
	        mockApp.setJob(job);
	        mockApp.setStatus(ApplicationStatus.APPLIED);

	        mockSchedule = new InterviewSchedule();
	        mockSchedule.setId(101);
	        mockSchedule.setApplication(mockApp);
	        mockSchedule.setInterviewDate(LocalDateTime.now().plusDays(2));
	        mockSchedule.setMode(InterviewType.VIRTUAL_INTERVIEW);
	        mockSchedule.setStatus(InterviewStatus.SCHEDULED);
	        mockSchedule.setMeetingLink("https://zoom.us/mock-link");
	    }
	    
	    
	    @Test
	    void saveSchedule_ShouldReturnDto_WhenInterviewNotExists() {
	        InterviewScheduleDto dto = new InterviewScheduleDto();
	        dto.setApplicationId(1);
	        dto.setInterviewDate(LocalDateTime.now().plusDays(1));
	        dto.setMode(InterviewType.VIRTUAL_INTERVIEW);
	        dto.setMeetingLink("https://zoom.us/mock");
	        dto.setStatus(InterviewStatus.SCHEDULED);

	        when(interviewRepo.existsByApplication_Id(1)).thenReturn(false);
	        when(applicationsRepo.findById(1)).thenReturn(Optional.of(mockApp));
	        when(interviewRepo.save(any())).thenAnswer(inv -> {
	            InterviewSchedule s = inv.getArgument(0);
	            s.setId(123);
	            return s;
	        });

	        InterviewScheduleDto saved = interviewService.saveSchedule(dto);

	        assertNotNull(saved);
	        assertEquals("Jaga", saved.getCandidateName());
	        assertEquals("Software Engineer", saved.getJobTitle());
	    }
	    
	    
	    
	    
	    @Test
	    void saveSchedule_ShouldThrow_WhenAlreadyScheduled() {
	        InterviewScheduleDto dto = new InterviewScheduleDto();
	        dto.setApplicationId(1);

	        when(interviewRepo.existsByApplication_Id(1)).thenReturn(true);

	        assertThrows(RuntimeException.class, () -> interviewService.saveSchedule(dto));
	    }

	    @Test
	    void getScheduleById_ShouldReturnDto_IfFound() {
	        when(interviewRepo.findById(101)).thenReturn(Optional.of(mockSchedule));

	        InterviewScheduleDto dto = interviewService.getScheduleById(101);

	        assertEquals(101, dto.getId());
	        assertEquals("Tech Corp", dto.getCompanyName());
	    }
	    @Test
	    void getScheduleById_ShouldReturnNull_IfNotFound() {
	        when(interviewRepo.findById(999)).thenReturn(Optional.empty());

	        InterviewScheduleDto result = interviewService.getScheduleById(999);
	        assertNull(result);
	    }

	    
	    @Test
	    void requestReschedule_ShouldUpdateStatus() {
	        when(interviewRepo.findById(101)).thenReturn(Optional.of(mockSchedule));

	        interviewService.requestReschedule(101, "Can't attend on current date");

	        verify(interviewRepo).save(any());
	        assertEquals(InterviewStatus.RESCHEDULE_REQUESTED, mockSchedule.getStatus());
	    }

	    
	    @Test
	    void requestReschedule_ShouldThrow_IfAlreadyRequested() {
	        mockSchedule.setStatus(InterviewStatus.RESCHEDULE_REQUESTED);
	        when(interviewRepo.findById(101)).thenReturn(Optional.of(mockSchedule));

	        assertThrows(RuntimeException.class, () -> interviewService.requestReschedule(101, "Already requested"));
	    }

	    
	    @Test
	    void cancelInterview_ShouldSetStatusToCancelled() {
	        when(interviewRepo.findById(101)).thenReturn(Optional.of(mockSchedule));

	        interviewService.cancelInterview(101, "Not available");

	        verify(interviewRepo).save(mockSchedule);
	        assertEquals(InterviewStatus.CANCELLED, mockSchedule.getStatus());
	        assertEquals("Not available", mockSchedule.getCancelReason());
	    }

	    
	    @Test
	    void approveReschedule_ShouldClearRequest() {
	        mockSchedule.setRescheduleRequest("Change timing");
	        when(interviewRepo.findById(101)).thenReturn(Optional.of(mockSchedule));

	        interviewService.approveReschedule(101);

	        verify(interviewRepo).save(mockSchedule);
	        assertNull(mockSchedule.getRescheduleRequest());
	    }

	    @Test
	    void rejectReschedule_ShouldSetRejectedStatus() {
	        mockSchedule.setRescheduleRequest("Need to reschedule");
	        when(interviewRepo.findById(101)).thenReturn(Optional.of(mockSchedule));

	        interviewService.rejectReschedule(101);

	        verify(interviewRepo).save(mockSchedule);
	        assertEquals(InterviewStatus.RESCHEDULE_REJECTED, mockSchedule.getStatus());
	        assertNull(mockSchedule.getRescheduleRequest());
	    }

	    @Test
	    void getByApplicationId_ShouldReturnInterview() {
	        when(interviewRepo.findByApplication_Id(1)).thenReturn(Optional.of(mockSchedule));

	        InterviewScheduleDto result = interviewService.getByApplicationId(1);

	        assertNotNull(result);
	        assertEquals("Software Engineer", result.getJobTitle());
	    }

	    @Test
	    void deleteSchedule_ShouldCallRepoDelete() {
	        doNothing().when(interviewRepo).deleteById(101);
	        interviewService.deleteSchedule(101);
	        verify(interviewRepo).deleteById(101);
	    }





}
