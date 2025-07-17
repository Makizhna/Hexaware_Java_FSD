package com.example.carrercrafter.ServiceImpl;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.carrercrafter.Service.JobSeekerResumeService;
import com.example.carrercrafter.dto.JobSeekerResumeDto;
import com.example.carrercrafter.entities.JobSeeker;
import com.example.carrercrafter.entities.JobSeekerResume;
import com.example.carrercrafter.mapper.JobSeekerResumeMapper;
import com.example.carrercrafter.repository.JobSeekerRepository;
import com.example.carrercrafter.repository.JobSeekerResumeRepository;

import jakarta.transaction.Transactional;

@Service
public class JobSeekerResumeServiceImpl implements JobSeekerResumeService {

    @Autowired
    private JobSeekerResumeRepository resumeRepository;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    /*@Override
    public JobSeekerResumeDto uploadResume(JobSeekerResumeDto dto) {
        Optional<JobSeeker> jobSeekerOpt = jobSeekerRepository.findById(dto.getJobSeekerId());
        if (jobSeekerOpt.isPresent()) {
            JobSeekerResume resume = JobSeekerResumeMapper.toEntity(dto, jobSeekerOpt.get());
            resume.setUploadedAt(LocalDate.now());
            JobSeekerResume saved = resumeRepository.save(resume);
            return JobSeekerResumeMapper.toDto(saved);
        }
        return null;
    }*/
    
    @Override
    public JobSeekerResumeDto uploadResume(MultipartFile file, int jobSeekerId) {
        Optional<JobSeeker> jobSeekerOpt = jobSeekerRepository.findById(jobSeekerId);
        if (jobSeekerOpt.isEmpty()) {
            throw new RuntimeException("Job Seeker not found");
        }

        try {
            //  Store the file temporarily (or save to S3/cloud later)
            String fileName = file.getOriginalFilename();
            String uploadPath = "uploads/resumes/" + fileName;

            java.nio.file.Path path = java.nio.file.Paths.get(uploadPath);
            java.nio.file.Files.createDirectories(path.getParent()); // Ensure dir exists
            java.nio.file.Files.write(path, file.getBytes());

            //  Save to database
            JobSeekerResume resume = new JobSeekerResume();
            resume.setFileName(fileName);
            resume.setFileUrl("http://localhost:8081/" + uploadPath);  //  can replace with public URL if needed
            resume.setUploadedAt(LocalDate.now());
            resume.setJobSeeker(jobSeekerOpt.get());

            JobSeekerResume saved = resumeRepository.save(resume);
            return JobSeekerResumeMapper.toDto(saved);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save resume file", e);
        }
    }


    @Override
    public JobSeekerResumeDto getResumeBySeekerId(int seekerId) {
        JobSeekerResume resume = resumeRepository.findByJobSeeker_SeekerId(seekerId);
        return resume != null ? JobSeekerResumeMapper.toDto(resume) : null;
    }

    @Override
    public JobSeekerResumeDto updateResume(int seekerId, JobSeekerResumeDto dto) {
        JobSeekerResume existing = resumeRepository.findByJobSeeker_SeekerId(seekerId);
        if (existing != null) {
            existing.setFileName(dto.getFileName());
            existing.setFileUrl(dto.getFileUrl());
            existing.setUploadedAt(LocalDate.now());
            return JobSeekerResumeMapper.toDto(resumeRepository.save(existing));
        }
        return null;
    }

    /*@Override
    public void deleteResume(int seekerId) {
        JobSeekerResume resume = resumeRepository.findByJobSeeker_SeekerId(seekerId);
        if (resume != null) {
            resumeRepository.delete(resume);
        }
    }*/
    
    
    @Transactional
    @Override
    public void deleteResume(int seekerId) {
        JobSeekerResume resume = resumeRepository.findByJobSeeker_SeekerId(seekerId);
        if (resume == null) throw new RuntimeException("Resume not found");

        // Delete file from disk
        Path filePath = Paths.get("uploads/resumes", resume.getFileName());
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete resume file", e);
        }

        // Unlink from parent JobSeeker
        JobSeeker jobSeeker = resume.getJobSeeker();
        if (jobSeeker != null) {
            jobSeeker.setJobSeekerResume(null);  // IMPORTANT: Unlink child
        }

        resumeRepository.delete(resume);
        resumeRepository.flush();
        System.out.println("Resume deleted from DB for seeker: " + seekerId);
    }



}