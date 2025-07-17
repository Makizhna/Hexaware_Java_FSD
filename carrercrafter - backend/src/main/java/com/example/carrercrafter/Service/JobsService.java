package com.example.carrercrafter.Service;
import com.example.carrercrafter.entities.Jobs;
import java.util.List;

import com.example.carrercrafter.dto.JobsDto;


public interface JobsService {

    JobsDto saveJob(JobsDto jobDto);
    List<JobsDto> getAllJobs();
    JobsDto getJobById(int id);
    List<JobsDto> getJobsByEmployer(int employerId);
    JobsDto updateJob(int id, JobsDto jobDto);
    void deleteJob(int id);
    
    
    List<JobsDto> getRecommendedJobsForSeeker(int seekerId);



}







   