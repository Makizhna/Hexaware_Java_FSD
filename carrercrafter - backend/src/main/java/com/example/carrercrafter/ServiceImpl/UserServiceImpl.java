package com.example.carrercrafter.ServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.carrercrafter.dto.UserDto;
import com.example.carrercrafter.entities.Applications;
import com.example.carrercrafter.entities.Employer;
import com.example.carrercrafter.entities.JobSeeker;
import com.example.carrercrafter.entities.JobSeekerResume;
import com.example.carrercrafter.entities.Jobs;
import com.example.carrercrafter.entities.User;
import com.example.carrercrafter.mapper.UserMapper;
import com.example.carrercrafter.repository.ApplicationsRepository;
import com.example.carrercrafter.repository.EmployerRepository;
import com.example.carrercrafter.repository.InterviewScheduleRepository;
import com.example.carrercrafter.repository.JobSeekerRepository;
import com.example.carrercrafter.repository.JobSeekerResumeRepository;
import com.example.carrercrafter.repository.JobsRepository;
import com.example.carrercrafter.repository.SavedJobsRepository;
import com.example.carrercrafter.repository.UserRepository;

import jakarta.transaction.Transactional;
import com.example.carrercrafter.Service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private JobSeekerRepository jobSeekerRepo;

    @Autowired
    private EmployerRepository employerRepo;
    
    @Autowired
    private ApplicationsRepository applicationsRepo;

    @Autowired
    private InterviewScheduleRepository interviewScheduleRepo;

    @Autowired
    private SavedJobsRepository savedJobsRepo;

    @Autowired
    private JobSeekerResumeRepository resumeRepository;

    @Autowired
    private JobsRepository jobsRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = userRepo.save(user);
        return UserMapper.mapToUserDto(saved);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                    .map(UserMapper::mapToUserDto)
                    .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(int id) {
        return userRepo.findById(id)
                .map(UserMapper::mapToUserDto)
                .orElse(null);
    }

    @Override
    public UserDto updateUser(int id, UserDto updatedUserDto) {
        return userRepo.findById(id).map(existingUser -> {
            existingUser.setName(updatedUserDto.getName());
            existingUser.setEmail(updatedUserDto.getEmail());
            existingUser.setPassword(updatedUserDto.getPassword());
            existingUser.setRole(updatedUserDto.getRole());
            return UserMapper.mapToUserDto(userRepo.save(existingUser));
        }).orElse(null);
    }

    
    @Override
    public UserDto login(String email, String password) {
        User user = userRepo.findByEmailAndPassword(email, password);
        return user != null ? UserMapper.mapToUserDto(user) : null;
    }

   
   
    
    @Override
    @Transactional
    public boolean deleteUserAndRelated(int id) {
    	
    	System.out.println("Deleting user: " + id);
    	System.out.println("Is JobSeeker? " + jobSeekerRepo.existsByUser_Id(id));
    	System.out.println("Is Employer? " + employerRepo.existsByUser_Id(id));
    	
        Optional<User> userOpt = userRepo.findById(id);
        if (userOpt.isEmpty()) return false;

        // 1. Delete JobSeeker-related entities
        if (jobSeekerRepo.existsByUser_Id(id)) {
            JobSeeker seeker = jobSeekerRepo.findByUser_Id(id);
            int seekerId = seeker.getSeekerId();

            // Delete InterviewSchedules and Applications
            List<Applications> apps = applicationsRepo.findByJobSeeker_SeekerId(seekerId);
            for (Applications app : apps) {
                interviewScheduleRepo.deleteByApplication_Id(app.getId());
            }
            applicationsRepo.deleteAll(apps);

            // Delete SavedJobs
            savedJobsRepo.deleteByJobSeeker_SeekerId(seekerId);

            // Delete resume if exists
            JobSeekerResume resume = resumeRepository.findByJobSeeker_SeekerId(seekerId);
            if (resume != null) resumeRepository.delete(resume);

            jobSeekerRepo.deleteById(seekerId);
        }

        //  2. Soft delete Employer and its jobs
        if (employerRepo.existsByUser_Id(id)) {
            Employer employer = employerRepo.findByUser_Id(id);
            int empId = employer.getEmployeeId();

            // Deactivate employer (soft delete)
            employer.setUser(null); //  Break FK before user delete
            employer.setActive(false);
            employerRepo.save(employer);   //Save update to prevent FK error

            // Deactivate jobs
            List<Jobs> jobs = jobsRepo.findByEmployer_EmployeeId(empId);
            for (Jobs job : jobs) {
                int jobId = job.getJobId();

                // Delete InterviewSchedules and Applications for each job
                if (applicationsRepo.existsByJob_JobId(jobId)) {
                    List<Applications> jobApps = applicationsRepo.findByJob_JobId(jobId);
                    for (Applications app : jobApps) {
                        interviewScheduleRepo.deleteByApplication_Id(app.getId());
                    }
                    applicationsRepo.deleteAll(jobApps);
                }

                // Delete SavedJobs for this job
                savedJobsRepo.deleteByJob_JobId(jobId);

                // Mark job inactive
                job.setActive(false);
                jobsRepo.save(job);
            }
        }

        //  3. Finally delete User
        userRepo.deleteById(id);

        return true;
    }
    
    
   
    
    
    
    
    
    
}
