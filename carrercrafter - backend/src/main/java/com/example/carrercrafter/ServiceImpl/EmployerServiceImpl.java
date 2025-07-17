package com.example.carrercrafter.ServiceImpl;

import com.example.carrercrafter.Service.EmployerService;
import com.example.carrercrafter.dto.EmployerDto;
import com.example.carrercrafter.entities.Applications;
import com.example.carrercrafter.entities.Employer;
import com.example.carrercrafter.entities.Jobs;
import com.example.carrercrafter.entities.User;
import com.example.carrercrafter.mapper.EmployerMapper;
import com.example.carrercrafter.repository.ApplicationsRepository;
import com.example.carrercrafter.repository.EmployerRepository;
import com.example.carrercrafter.repository.InterviewScheduleRepository;
import com.example.carrercrafter.repository.JobsRepository;
import com.example.carrercrafter.repository.SavedJobsRepository;
import com.example.carrercrafter.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EmployerServiceImpl implements EmployerService {

    @Autowired
    private EmployerRepository employerRepo;

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private JobsRepository jobsRepo;

    @Autowired
    private ApplicationsRepository applicationRepo;

    @Autowired
    private InterviewScheduleRepository interviewScheduleRepo;
    
    @Autowired
    private SavedJobsRepository savedJobsRepo;


    
    @Override
    public EmployerDto saveEmployer(EmployerDto employerDto) {
        int userId = employerDto.getUser().getId();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employer employer = EmployerMapper.toEntity(employerDto, user);
         employer.setActive(true);
        Employer saved = employerRepo.save(employer);
        return EmployerMapper.toDto(saved);
    }


    /*@Override
    public List<EmployerDto> getAllEmployers() {
        List<Employer> employers = employerRepo.findAll();
        List<EmployerDto> dtoList = new ArrayList<>();

        for (Employer emp : employers) {
            dtoList.add(EmployerMapper.toDto(emp));
        }

        return dtoList;
    }*/
    
    @Override
    public List<EmployerDto> getAllEmployers() {
        List<Employer> employers = employerRepo.findByActiveTrue(); 
        List<EmployerDto> dtoList = new ArrayList<>();

        for (Employer emp : employers) {
            dtoList.add(EmployerMapper.toDto(emp));
        }

        return dtoList;
    }


    
    @Override
    public EmployerDto getEmployerById(int id) {
        Employer emp = employerRepo.findById(id).orElse(null);
        return emp != null ? EmployerMapper.toDto(emp) : null;
    }

    
    @Override
    public EmployerDto updateEmployer(int id, EmployerDto employerDto) {
        Optional<Employer> optionalEmp = employerRepo.findById(id);
        if (optionalEmp.isPresent()) {
            Employer existing = optionalEmp.get();

            existing.setName(employerDto.getName());
            existing.setCompanyName(employerDto.getCompanyName());
            existing.setPosition(employerDto.getPosition());
            existing.setWorkEmail(employerDto.getWorkEmail());        
            existing.setLinkedinUrl(employerDto.getLinkedinUrl());    
            existing.setLocation(employerDto.getLocation());

            int userId = employerDto.getUser().getId();
            User user = userRepo.findById(userId).orElse(null);
            existing.setUser(user);

            Employer saved = employerRepo.save(existing);
            return EmployerMapper.toDto(saved);
        }

        return null;
    }

  
    
    @Override
    public EmployerDto getByUserId(int userId) {
        Employer emp = employerRepo.findByUser_Id(userId);
        return emp != null ? EmployerMapper.toDto(emp) : null;
        
    }

    
    @Override
    public List<EmployerDto> searchByLocation(String location) {
        List<Employer> employers = employerRepo.findByLocationContainingIgnoreCase(location);
        List<EmployerDto> dtos = new ArrayList<>();

        for (Employer emp : employers) {
            dtos.add(EmployerMapper.toDto(emp));
        }

        return dtos;
    }
    
  
    /*@Override
    public void deleteEmployer(int id) {
        Employer emp = employerRepo.findById(id).orElse(null);
        if (emp != null) {
            List<Jobs> jobs = jobsRepo.findByEmployerEmployeeId(id);
            for (Jobs job : jobs) {
                List<Applications> apps = applicationRepo.findByJob_JobId(job.getJobId());
                for (Applications app : apps) {
                    interviewScheduleRepo.deleteByApplication_Id(app.getId()
);
                }
                applicationRepo.deleteAll(apps);
                jobsRepo.deleteById(job.getJobId());
            }

            int userId = emp.getUser().getId();
            employerRepo.deleteById(id);
            userRepo.deleteById(userId);
        }
    }
    
    @Override
    public void deleteEmployer(int id) {
        Employer emp = employerRepo.findById(id).orElse(null);
        if (emp != null) {
            emp.setActive(false); // Soft delete
            employerRepo.save(emp); //  Save the updated status
        }
    }*/
    
    
    @Override
    @Transactional
    public void deleteEmployer(int id) {
        Employer emp = employerRepo.findById(id).orElse(null);
        if (emp != null) {
            User user = emp.getUser(); // get user first
            int userId = (user != null) ? user.getId() : -1;

            // 1. Break circular link: Employer → User
            emp.setUser(null);
            emp.setActive(false); // soft delete
            employerRepo.save(emp); // safe update

            // 2. Soft delete all jobs by this employer
            List<Jobs> jobs = jobsRepo.findByEmployer_EmployeeId(emp.getEmployeeId());
            for (Jobs job : jobs) {
                int jobId = job.getJobId();

                // 2a. Delete all interviews & applications related to this job
                List<Applications> apps = applicationRepo.findByJob_JobId(jobId);
                for (Applications app : apps) {
                    interviewScheduleRepo.deleteByApplication_Id(app.getId());
                }
                applicationRepo.deleteAll(apps);

                // 2b. Delete saved jobs for this job
                savedJobsRepo.deleteByJob_JobId(jobId);

                // 2c. Soft delete job
                job.setActive(false);
                jobsRepo.save(job);
            }

            // 3. Finally, delete the linked user
            if (userId != -1) {
                userRepo.deleteById(userId);
            }
        }
    }


}
