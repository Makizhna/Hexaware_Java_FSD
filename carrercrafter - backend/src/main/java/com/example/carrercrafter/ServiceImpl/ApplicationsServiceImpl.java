package com.example.carrercrafter.ServiceImpl;

import com.example.carrercrafter.Service.ApplicationsService;
import com.example.carrercrafter.dto.ApplicationsDto;
import com.example.carrercrafter.entities.Applications;
import com.example.carrercrafter.entities.JobSeeker;
import com.example.carrercrafter.entities.JobSeekerResume;
import com.example.carrercrafter.entities.Jobs;
import com.example.carrercrafter.enums.ApplicationStatus;
import com.example.carrercrafter.mapper.ApplicationsMapper;
import com.example.carrercrafter.repository.ApplicationsRepository;
import com.example.carrercrafter.repository.InterviewScheduleRepository;
import com.example.carrercrafter.repository.JobSeekerRepository;
import com.example.carrercrafter.repository.JobSeekerResumeRepository;
import com.example.carrercrafter.repository.JobsRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationsServiceImpl implements ApplicationsService {

    @Autowired
    private ApplicationsRepository applicationsRepo;

    @Autowired
    private JobSeekerRepository jobSeekerRepo;

    @Autowired
    private JobsRepository jobsRepo;
    
    @Autowired
    private InterviewScheduleRepository interviewScheduleRepo;
    
    @Autowired 
    private JobSeekerResumeRepository resumeRepo;

    @Override
    public ApplicationsDto saveApplication(ApplicationsDto dto) {
    	
        Jobs job = jobsRepo.findById(dto.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
       //  Add employer active check here:
        if (!job.getEmployer().isActive()) {
            throw new RuntimeException("This employer is no longer active. You cannot apply for this job.");
        }
        
     // Check duplicate
        boolean alreadyApplied = applicationsRepo.existsByJob_JobIdAndJobSeeker_SeekerId(dto.getJobId(), dto.getJobSeekerId());
        if (alreadyApplied) {
            throw new RuntimeException("You have already applied for this job.");
        }
        
        
        JobSeekerResume resume = resumeRepo.findById(dto.getResumeId())
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        
        JobSeeker seeker = jobSeekerRepo.findById(dto.getJobSeekerId())
                .orElseThrow(() -> new RuntimeException("JobSeeker not found"));

        Applications app = ApplicationsMapper.mapToEntity(dto, seeker, job, resume);
        app.setStatus(ApplicationStatus.APPLIED); 
        Applications saved = applicationsRepo.save(app);

        return ApplicationsMapper.mapToDto(saved);
    }

    @Override
    public List<ApplicationsDto> getAllApplications() {
        List<Applications> apps = applicationsRepo.findAll();
        List<ApplicationsDto> dtos = new ArrayList<>();
        for (Applications app : apps) {
            dtos.add(ApplicationsMapper.mapToDto(app));
        }
        return dtos;
    }

    @Override
    public ApplicationsDto getApplicationById(int id) {
        Optional<Applications> opt = applicationsRepo.findById(id);
        return opt.map(ApplicationsMapper::mapToDto).orElse(null);
    }

    @Override
    public ApplicationsDto updateApplication(int id, ApplicationsDto dto) {
        Optional<Applications> optionalApp = applicationsRepo.findById(id);
        if (optionalApp.isPresent()) {
            Applications existing = optionalApp.get();

            // Fetch JobSeeker and Job from their IDs in DTO
            JobSeeker seeker = jobSeekerRepo.findById(dto.getJobSeekerId())
                    .orElseThrow(() -> new RuntimeException("JobSeeker not found"));
            Jobs job = jobsRepo.findById(dto.getJobId())
                    .orElseThrow(() -> new RuntimeException("Job not found"));
            JobSeekerResume resume = resumeRepo.findById(dto.getResumeId())
                    .orElseThrow(() -> new RuntimeException("Resume not found"));

            existing.setJobSeeker(seeker);
            existing.setJob(job);
            existing.setResume(resume);
            existing.setStatus(dto.getStatus());
            existing.setAppliedDate(dto.getAppliedDate());

            return ApplicationsMapper.mapToDto(applicationsRepo.save(existing));
        }
        return null;
    }

    
    @Transactional
    @Override
    public void deleteApplication(int id) {
        interviewScheduleRepo.deleteByApplication_Id(id);
        applicationsRepo.deleteById(id);
    }
    
    
    
    @Override
    public List<ApplicationsDto> getApplicationsByJobSeekerId(int seekerId) {
        List<Applications> apps = applicationsRepo.findByJobSeeker_SeekerId(seekerId);
        List<ApplicationsDto> dtos = new ArrayList<>();
        for (Applications app : apps) {
            dtos.add(ApplicationsMapper.mapToDto(app));
        }
        return dtos;
    }

    @Override
    public List<ApplicationsDto> getApplicationsByJobId(int jobId) {
        List<Applications> apps = applicationsRepo.findByJob_JobId(jobId);
        List<ApplicationsDto> dtos = new ArrayList<>();
        for (Applications app : apps) {
            dtos.add(ApplicationsMapper.mapToDto(app));
        }
        return dtos;
    }

    
    @Override
    public List<ApplicationsDto> getApplicationsByEmployerId(int employerId) {
        List<Applications> apps = applicationsRepo.findByJob_Employer_EmployeeId(employerId);
        List<ApplicationsDto> dtos = new ArrayList<>();
        for (Applications app : apps) {
            dtos.add(ApplicationsMapper.mapToDto(app));
        }
        return dtos;
    }

    @Override
    public ApplicationsDto updateApplicationStatus(int applicationId, String status) {
        Optional<Applications> opt = applicationsRepo.findById(applicationId);
        if (opt.isPresent()) {
            Applications app = opt.get();
            app.setStatus(ApplicationStatus.valueOf(status));
            return ApplicationsMapper.mapToDto(applicationsRepo.save(app));
        }
        return null;
    }


}
