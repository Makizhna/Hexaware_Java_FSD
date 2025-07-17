package com.example.carrercrafter.ServiceImpl;

import com.example.carrercrafter.Service.JobsService;
import com.example.carrercrafter.dto.JobsDto;
import com.example.carrercrafter.entities.Applications;
import com.example.carrercrafter.entities.Employer;
import com.example.carrercrafter.entities.JobSeeker;
import com.example.carrercrafter.entities.Jobs;
import com.example.carrercrafter.mapper.JobsMapper;
import com.example.carrercrafter.repository.ApplicationsRepository;
import com.example.carrercrafter.repository.EmployerRepository;
import com.example.carrercrafter.repository.InterviewScheduleRepository;
import com.example.carrercrafter.repository.JobSeekerRepository;
import com.example.carrercrafter.repository.JobsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class JobsServiceImpl implements JobsService {

    @Autowired
    private JobsRepository jobsRepo;
    
    @Autowired
    private JobSeekerRepository jobSeekerRepo;


    @Autowired
    private EmployerRepository employerRepo;
    
    @Autowired
    private ApplicationsRepository applicationRepo;
    
    @Autowired
    private InterviewScheduleRepository interviewScheduleRepo;


    @Override
    public JobsDto saveJob(JobsDto jobDto) {
        //int employerId = jobDto.getEmployerId();
    	int employerId = jobDto.getEmployer().getEmployeeId();
        Employer employer = employerRepo.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer not found with ID: " + employerId));

        Jobs job = JobsMapper.mapToJobs(jobDto, employer);
        Jobs saved = jobsRepo.save(job);
        return JobsMapper.mapToJobsDto(saved);
    }

    
    /*@Override
    public List<JobsDto> getAllJobs() {
        List<Jobs> jobs = jobsRepo.findAll();
        List<JobsDto> dtoList = new ArrayList<>();
        for (Jobs job : jobs) {
            dtoList.add(JobsMapper.mapToJobsDto(job));
        }
        return dtoList;
    }*/

    
    @Override
    public JobsDto getJobById(int id) {
        Optional<Jobs> jobOpt = jobsRepo.findById(id);
        return jobOpt.map(JobsMapper::mapToJobsDto).orElse(null);
    }
    
    @Override
    public List<JobsDto> getAllJobs() {
        List<Jobs> jobs = jobsRepo.findAll();
        List<JobsDto> dtoList = new ArrayList<>();
        for (Jobs job : jobs) {
            if (job.getEmployer() != null && job.getEmployer().isActive()) {
                dtoList.add(JobsMapper.mapToJobsDto(job));
            }
        }
        return dtoList;
    }
    
    @Override
    public List<JobsDto> getJobsByEmployer(int employerId) {
        List<Jobs> jobs = jobsRepo.findByEmployer_EmployeeId(employerId);
        return jobs.stream()
            .map(JobsMapper::mapToJobsDto)
            .collect(Collectors.toList());
    }



    @Override
    public JobsDto updateJob(int id, JobsDto jobDto) {
        Optional<Jobs> jobOpt = jobsRepo.findById(id);

        if (jobOpt.isPresent()) {
            Jobs existing = jobOpt.get();
            existing.setTitle(jobDto.getTitle());
            existing.setDescription(jobDto.getDescription());
            existing.setLocation(jobDto.getLocation());
            existing.setQualifications(jobDto.getQualifications());
            existing.setSalary(jobDto.getSalary());
            existing.setPostedDate(jobDto.getPostedDate());
            existing.setLocationType(jobDto.getLocationType());
            existing.setJobType(jobDto.getJobType());
            existing.setSkillsRequired(jobDto.getSkillsRequired());
            existing.setJobExperience(jobDto.getJobExperience());
            existing.setApplicationDeadline(jobDto.getApplicationDeadline());
            existing.setApplicationEmail(jobDto.getApplicationEmail());
            existing.setActive(jobDto.isActive());

            //  use getEmployer().getEmployeeId()
            Employer employer = employerRepo.findById(jobDto.getEmployer().getEmployeeId()).orElse(null);
            existing.setEmployer(employer);

            Jobs updated = jobsRepo.save(existing);
            return JobsMapper.mapToJobsDto(updated);
        }

        return null;
    }


    @Override
    public void deleteJob(int id) {
        //  Check and delete related applications and their interview schedules
        if (applicationRepo.existsByJob_JobId(id)) {
            List<Applications> apps = applicationRepo.findByJob_JobId(id);

            for (Applications app : apps) {
                // Delete interview schedules linked to this application
                interviewScheduleRepo.deleteByApplication_Id(app.getId());
            }

            //  Delete all applications for this job
            applicationRepo.deleteAll(apps);
        }

        // Now safely delete the job
        jobsRepo.deleteById(id);
    }
    
    
    
    @Override
    public List<JobsDto> getRecommendedJobsForSeeker(int seekerId) {
        JobSeeker seeker = jobSeekerRepo.findById(seekerId)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found"));

        String skills = seeker.getSkills();
        if (skills == null || skills.trim().isEmpty()) return Collections.emptyList();

        Set<Jobs> matched = new HashSet<>();
        for (String skill : skills.split(",")) {
            matched.addAll(jobsRepo.findBySkillsRequiredContainingIgnoreCase(skill.trim()));
        }

        return matched.stream()
                .filter(job -> job.getEmployer() != null && job.getEmployer().isActive()) 
                .map(JobsMapper::mapToJobsDto)
                .toList();
    }




	
}
