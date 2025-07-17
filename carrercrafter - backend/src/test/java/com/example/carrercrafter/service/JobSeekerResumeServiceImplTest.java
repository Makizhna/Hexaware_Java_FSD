package com.example.carrercrafter.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import com.example.carrercrafter.ServiceImpl.JobSeekerResumeServiceImpl;
import com.example.carrercrafter.dto.JobSeekerResumeDto;
import com.example.carrercrafter.entities.JobSeeker;
import com.example.carrercrafter.entities.JobSeekerResume;
import com.example.carrercrafter.repository.JobSeekerRepository;
import com.example.carrercrafter.repository.JobSeekerResumeRepository;

public class JobSeekerResumeServiceImplTest {

    @InjectMocks
    private JobSeekerResumeServiceImpl resumeService;

    @Mock
    private JobSeekerResumeRepository resumeRepository;

    @Mock
    private JobSeekerRepository jobSeekerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadResume() throws IOException {
        JobSeeker seeker = new JobSeeker();
        seeker.setSeekerId(1);

        MockMultipartFile file = new MockMultipartFile("resume", "resume.pdf", "application/pdf", "test resume".getBytes());

        when(jobSeekerRepository.findById(1)).thenReturn(Optional.of(seeker));
        when(resumeRepository.save(any(JobSeekerResume.class))).thenAnswer(i -> i.getArgument(0));

        JobSeekerResumeDto dto = resumeService.uploadResume(file, 1);

        assertNotNull(dto);
        assertEquals("resume.pdf", dto.getFileName());
        assertTrue(dto.getFileUrl().contains("uploads/resumes"));
        verify(resumeRepository, times(1)).save(any(JobSeekerResume.class));
    }

    @Test
    void testGetResumeBySeekerId() {
        JobSeekerResume resume = new JobSeekerResume();
        resume.setResumeId(1);
        resume.setFileName("resume.pdf");
        resume.setFileUrl("uploads/resumes/resume.pdf");
        resume.setUploadedAt(LocalDate.now());

        when(resumeRepository.findByJobSeeker_SeekerId(1)).thenReturn(resume);

        JobSeekerResumeDto result = resumeService.getResumeBySeekerId(1);

        assertNotNull(result);
        assertEquals("resume.pdf", result.getFileName());
    }

    @Test
    void testUpdateResume() {
        JobSeekerResume existing = new JobSeekerResume();
        existing.setResumeId(1);
        existing.setFileName("old.pdf");

        when(resumeRepository.findByJobSeeker_SeekerId(1)).thenReturn(existing);
        when(resumeRepository.save(any(JobSeekerResume.class))).thenAnswer(i -> i.getArgument(0));

        JobSeekerResumeDto updateDto = new JobSeekerResumeDto();
        updateDto.setFileName("new.pdf");
        updateDto.setFileUrl("uploads/resumes/new.pdf");

        JobSeekerResumeDto result = resumeService.updateResume(1, updateDto);

        assertEquals("new.pdf", result.getFileName());
        verify(resumeRepository, times(1)).save(any(JobSeekerResume.class));
    }

    @Test
    void testDeleteResume() {
        JobSeekerResume resume = new JobSeekerResume();
        resume.setResumeId(1);

        when(resumeRepository.findByJobSeeker_SeekerId(1)).thenReturn(resume);

        resumeService.deleteResume(1);

        verify(resumeRepository, times(1)).delete(resume);
    }
}
