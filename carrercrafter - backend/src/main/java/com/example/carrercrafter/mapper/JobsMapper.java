package com.example.carrercrafter.mapper;

import com.example.carrercrafter.dto.JobsDto;
import com.example.carrercrafter.entities.Jobs;
import com.example.carrercrafter.entities.Employer;

public class JobsMapper {

    public static JobsDto mapToJobsDto(Jobs job) {
        if (job == null) return null;

        JobsDto dto = new JobsDto();
        dto.setJobId(job.getJobId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setLocation(job.getLocation());
        dto.setQualifications(job.getQualifications());
        dto.setSalary(job.getSalary());
        dto.setPostedDate(job.getPostedDate());
        //dto.setEmployerId(job.getEmployer() != null ? job.getEmployer().getEmployeeId() : 0);
        dto.setEmployer(EmployerMapper.toDto(job.getEmployer()));
        dto.setLocationType(job.getLocationType());
        dto.setJobType(job.getJobType());
        dto.setSkillsRequired(job.getSkillsRequired());
        dto.setJobExperience(job.getJobExperience());
        dto.setApplicationDeadline(job.getApplicationDeadline());
        dto.setApplicationInstructions(job.getApplicationInstructions());
        dto.setApplicationEmail(job.getApplicationEmail());

        
        dto.setCreatedAt(job.getCreatedAt());
        dto.setActive(job.isActive());

        return dto;
    }

    public static Jobs mapToJobs(JobsDto dto, Employer employer) {
        if (dto == null) return null;

        Jobs job = new Jobs();
        job.setJobId(dto.getJobId());
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setQualifications(dto.getQualifications());
        job.setSalary(dto.getSalary());
        job.setPostedDate(dto.getPostedDate());
        job.setEmployer(employer);
        job.setLocationType(dto.getLocationType());
        job.setJobType(dto.getJobType());
        job.setSkillsRequired(dto.getSkillsRequired());
        job.setJobExperience(dto.getJobExperience());
        job.setApplicationDeadline(dto.getApplicationDeadline());
        job.setApplicationEmail(dto.getApplicationEmail());
        job.setApplicationInstructions(dto.getApplicationInstructions());
        job.setActive(dto.isActive());

        //  Do NOT set createdAt here. It is auto-generated via @CreationTimestamp.

        return job;
    }
}
