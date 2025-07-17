package com.example.carrercrafter.mapper;

import com.example.carrercrafter.dto.ApplicationsDto;
import com.example.carrercrafter.entities.Applications;
import com.example.carrercrafter.entities.JobSeeker;
import com.example.carrercrafter.entities.Jobs;
import com.example.carrercrafter.enums.ApplicationStatus;
import com.example.carrercrafter.entities.JobSeekerResume;

public class ApplicationsMapper {

    /*public static ApplicationsDto mapToDto(Applications app) {
        if (app == null) return null;

        ApplicationsDto dto = new ApplicationsDto();
        dto.setId(app.getId());
        dto.setJobId(app.getJob().getJobId());
        dto.setJobSeekerId(app.getJobSeeker().getSeekerId());
        dto.setResumeId(app.getResume().getResumeId());
        dto.setResumeUrl(app.getResume().getFileUrl());
        dto.setJobTitle(app.getJob().getTitle());
        dto.setCompanyName(app.getJob().getEmployer().getCompanyName());
        //dto.setStatus(app.getStatus());
        dto.setStatus(app.getStatus() != null ? app.getStatus() : ApplicationStatus.APPLIED);
        dto.setAppliedDate(app.getAppliedDate());
        return dto;
    }*/
    
    public static ApplicationsDto mapToDto(Applications app) {
        if (app == null) return null;

        ApplicationsDto dto = new ApplicationsDto();
        dto.setId(app.getId());
        dto.setJobId(app.getJob().getJobId());
        dto.setJobSeekerId(app.getJobSeeker().getSeekerId());
        dto.setJobTitle(app.getJob().getTitle());
        dto.setCompanyName(app.getJob().getEmployer().getCompanyName());
        dto.setStatus(app.getStatus() != null ? app.getStatus() : ApplicationStatus.APPLIED);
        dto.setAppliedDate(app.getAppliedDate());
        dto.setCoverLetter(app.getCoverLetter()); 
        dto.setJobSeekerName(app.getJobSeeker().getName());



        if (app.getResume() != null) {
            dto.setResumeId(app.getResume().getResumeId());
            dto.setResumeUrl(app.getResume().getFileUrl());
        } else {
            dto.setResumeId(0);
            dto.setResumeUrl(null);
        }

        return dto;
    }


    public static Applications mapToEntity(ApplicationsDto dto, JobSeeker seeker, Jobs job, JobSeekerResume resume) {
        if (dto == null) return null;

        Applications app = new Applications();
        app.setId(dto.getId());
        app.setJob(job);
        app.setJobSeeker(seeker);
        app.setResume(resume);  
        app.setStatus(dto.getStatus());
        app.setAppliedDate(dto.getAppliedDate());
        app.setCoverLetter(dto.getCoverLetter());
        return app;
    }

}
