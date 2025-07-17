package com.example.carrercrafter.mapper;

import java.time.LocalDate;

import com.example.carrercrafter.dto.SavedJobsDto;
import com.example.carrercrafter.entities.SavedJobs;
import com.example.carrercrafter.entities.Jobs;
import com.example.carrercrafter.entities.JobSeeker;

public class SavedJobsMapper {

    public static SavedJobsDto mapToDto(SavedJobs saved) {
        if (saved == null) return null;

        SavedJobsDto dto = new SavedJobsDto();
        dto.setId(saved.getId());
        dto.setJobId(saved.getJob().getJobId());
        dto.setJobSeekerId(saved.getJobSeeker().getSeekerId());
        dto.setSavedDate(saved.getSavedDate());
        dto.setCompanyName(saved.getJob().getEmployer().getCompanyName());
        dto.setApplicationDeadline(saved.getJob().getApplicationDeadline().toString());
        dto.setJobTitle(saved.getJob().getTitle());


        return dto;
    }

    public static SavedJobs mapToEntity(SavedJobsDto dto, JobSeeker seeker, Jobs job) {
        if (dto == null) return null;

        SavedJobs saved = new SavedJobs();

        // Set ID only if provided (non-zero)
        if (dto.getId() != 0) {
            saved.setId(dto.getId());
        }

        // Set the job seeker if not null
        if (seeker != null) {
            saved.setJobSeeker(seeker);
        } else {
            System.out.println(" JobSeeker is null in mapToEntity!");
        }

        // Set the job if not null
        if (job != null) {
            saved.setJob(job);
        } else {
            System.out.println(" Job is null in mapToEntity!");
        }

        saved.setSavedDate(
        	    dto.getSavedDate() != null ? dto.getSavedDate() : LocalDate.now()
        	);


        return saved;
    }

}
