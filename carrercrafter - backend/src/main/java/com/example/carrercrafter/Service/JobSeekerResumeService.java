package com.example.carrercrafter.Service;

import org.springframework.web.multipart.MultipartFile;

import com.example.carrercrafter.dto.JobSeekerResumeDto;

public interface JobSeekerResumeService {
    //JobSeekerResumeDto uploadResume(JobSeekerResumeDto dto);
    JobSeekerResumeDto uploadResume(MultipartFile file, int jobSeekerId);
    JobSeekerResumeDto getResumeBySeekerId(int seekerId);
    JobSeekerResumeDto updateResume(int seekerId, JobSeekerResumeDto dto);
    void deleteResume(int seekerId);
}
