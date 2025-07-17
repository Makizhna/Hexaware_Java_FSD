package com.example.carrercrafter.mapper;

import com.example.carrercrafter.dto.JobSeekerResumeDto;
import com.example.carrercrafter.entities.JobSeeker;
import com.example.carrercrafter.entities.JobSeekerResume;

public class JobSeekerResumeMapper {

    public static JobSeekerResumeDto toDto(JobSeekerResume resume) {
        JobSeekerResumeDto dto = new JobSeekerResumeDto();
        dto.setResumeId(resume.getResumeId());
        dto.setFileName(resume.getFileName());
        dto.setFileUrl(resume.getFileUrl());
        dto.setUploadedAt(resume.getUploadedAt());
        if (resume.getJobSeeker() != null) {
            dto.setJobSeekerId(resume.getJobSeeker().getSeekerId());
        }
        return dto;
    }

    public static JobSeekerResume toEntity(JobSeekerResumeDto dto, JobSeeker jobSeeker) {
        JobSeekerResume resume = new JobSeekerResume();
        resume.setResumeId(dto.getResumeId());
        resume.setFileName(dto.getFileName());
        resume.setFileUrl(dto.getFileUrl());
        resume.setUploadedAt(dto.getUploadedAt());
        resume.setJobSeeker(jobSeeker);
        return resume;
    }

    public static JobSeekerResume toEntity(JobSeekerResumeDto dto) {
        JobSeekerResume resume = new JobSeekerResume();
        resume.setResumeId(dto.getResumeId());
        resume.setFileName(dto.getFileName());
        resume.setFileUrl(dto.getFileUrl());
        resume.setUploadedAt(dto.getUploadedAt());
        return resume;
    }
}
