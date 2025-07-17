package com.example.carrercrafter.ServiceImpl;

import com.example.carrercrafter.Service.JobSeekerService;
import com.example.carrercrafter.dto.JobSeekerDto;
import com.example.carrercrafter.dto.JobSeekerResumeDto;
import com.example.carrercrafter.entities.Applications;
import com.example.carrercrafter.entities.JobSeeker;
import com.example.carrercrafter.entities.JobSeekerResume;
import com.example.carrercrafter.entities.User;
import com.example.carrercrafter.mapper.JobSeekerMapper;
import com.example.carrercrafter.mapper.JobSeekerResumeMapper;
import com.example.carrercrafter.repository.ApplicationsRepository;
import com.example.carrercrafter.repository.InterviewScheduleRepository;
import com.example.carrercrafter.repository.JobSeekerRepository;
import com.example.carrercrafter.repository.SavedJobsRepository;
import com.example.carrercrafter.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobSeekerServiceImpl implements JobSeekerService {

    @Autowired
    private JobSeekerRepository jobSeekerRepo;

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private ApplicationsRepository applicationsRepo;

    @Autowired
    private InterviewScheduleRepository interviewScheduleRepo;

    @Autowired
    private SavedJobsRepository savedJobsRepo;

    //  Save JobSeeker with user reference
    @Override
    public JobSeekerDto saveJobSeeker(JobSeekerDto dto) {
        int userId = dto.getUser().getId();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        JobSeeker jobSeeker = JobSeekerMapper.toEntity(dto);
        jobSeeker.setUser(user);  // Link the full user
        
     // Link resume back to job seeker
        JobSeekerResumeDto resumeDto = dto.getJobSeekerResume();
        if (resumeDto != null) {
            JobSeekerResume resume = JobSeekerResumeMapper.toEntity(resumeDto);
            resume.setJobSeeker(jobSeeker);  // important for @OneToOne
            jobSeeker.setJobSeekerResume(resume);
        }

        return JobSeekerMapper.toDto(jobSeekerRepo.save(jobSeeker));
    }

    //  Get all job seekers
    @Override
    public List<JobSeekerDto> getAllJobSeekers() {
        List<JobSeekerDto> dtos = new ArrayList<>();
        for (JobSeeker js : jobSeekerRepo.findAll()) {
            dtos.add(JobSeekerMapper.toDto(js));
        }
        return dtos;
    }

    //  Get job seeker by ID
    @Override
    public JobSeekerDto getJobSeekerById(int id) {
        return jobSeekerRepo.findById(id)
                .map(JobSeekerMapper::toDto)
                .orElse(null);
    }

    
    @Override
    public JobSeekerDto updateJobSeeker(int id, JobSeekerDto dto) {
        Optional<JobSeeker> optional = jobSeekerRepo.findById(id);
        if (optional.isPresent()) {
            JobSeeker existing = optional.get();

            // Update fields
            existing.setName(dto.getName());
            existing.setEducation(dto.getEducation());
            existing.setSkills(dto.getSkills());
            existing.setExperience(dto.getExperience());
            existing.setPhone(dto.getPhone());
            existing.setDob(dto.getDob());
            existing.setLocation(dto.getLocation());
            existing.setLinkedinUrl(dto.getLinkedinUrl());
            existing.setGithubUrl(dto.getGithubUrl());

            // Optional: only update user if present
            if (dto.getUser() != null) {
                int userId = dto.getUser().getId();
                User user = userRepo.findById(userId).orElse(null);
                existing.setUser(user);
            }
            
            if (dto.getJobSeekerResume() != null) {
                JobSeekerResume updatedResume = JobSeekerResumeMapper.toEntity(dto.getJobSeekerResume());
                updatedResume.setJobSeeker(existing);
                existing.setJobSeekerResume(updatedResume);
            }

            return JobSeekerMapper.toDto(jobSeekerRepo.save(existing));
        }
        return null;
    }

    
    //@Override
    //public void deleteJobSeeker(int id) {
    	//Optional<JobSeeker> optional = jobSeekerRepo.findById(id);
        //if (optional.isPresent()) {
           // JobSeeker jobSeeker = optional.get();
           // int userId = jobSeeker.getUser().getId();

            // First delete JobSeeker
           // jobSeekerRepo.deleteById(id);

            // Then delete associated User
          //  userRepo.deleteById(userId);
        //}
  //  }
      

    
    @Override
    public JobSeekerDto getByUserId(int userId) {
        JobSeeker js = jobSeekerRepo.findByUser_Id(userId);
        return js != null ? JobSeekerMapper.toDto(js) : null;
    }

    
    @Override
    public List<JobSeekerDto> searchBySkill(String skill) {
        List<JobSeekerDto> dtos = new ArrayList<>();
        for (JobSeeker js : jobSeekerRepo.findBySkillsContainingIgnoreCase(skill)) {
            dtos.add(JobSeekerMapper.toDto(js));
        }
        return dtos;
    }

    
    @Override
    public List<JobSeekerDto> searchBySkillsAndExperience(String skill, String experience) {
        List<JobSeekerDto> dtos = new ArrayList<>();
        for (JobSeeker js : jobSeekerRepo.findBySkillsContainingIgnoreCaseAndExperienceContainingIgnoreCase(skill, experience)) {
            dtos.add(JobSeekerMapper.toDto(js));
        }
        return dtos;
    }
    
    
    @Override
    @Transactional
    public void deleteJobSeeker(int id) {
        JobSeeker seeker = jobSeekerRepo.findById(id).orElse(null);
        if (seeker != null) {
            List<Applications> apps = applicationsRepo.findByJobSeeker_SeekerId(id);
            for (Applications app : apps) {
                interviewScheduleRepo.deleteByApplication_Id(app.getId());
            }
            applicationsRepo.deleteAll(apps);

            savedJobsRepo.deleteByJobSeeker_SeekerId(id);

            int userId = seeker.getUser().getId();
            jobSeekerRepo.deleteById(id);
            userRepo.deleteById(userId);
        }
    }

}
