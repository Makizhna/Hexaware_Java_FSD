package com.example.carrercrafter.Service;

import java.util.List;

import com.example.carrercrafter.dto.JobSeekerDto;


public interface JobSeekerService {

    JobSeekerDto saveJobSeeker(JobSeekerDto jobSeekerDto);
    List<JobSeekerDto> getAllJobSeekers();
    JobSeekerDto getJobSeekerById(int id);
    JobSeekerDto updateJobSeeker(int id, JobSeekerDto jobSeekerDto);
    void deleteJobSeeker(int id);
    JobSeekerDto getByUserId(int userId);
    List<JobSeekerDto> searchBySkill(String skill);
    List<JobSeekerDto> searchBySkillsAndExperience(String skills, String experience);



}

