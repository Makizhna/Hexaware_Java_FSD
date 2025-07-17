package com.example.carrercrafter.ServiceImpl;

import com.example.carrercrafter.Service.SavedJobsService;
import com.example.carrercrafter.dto.SavedJobsDto;
import com.example.carrercrafter.entities.Jobs;
import com.example.carrercrafter.entities.JobSeeker;
import com.example.carrercrafter.entities.SavedJobs;
import com.example.carrercrafter.mapper.SavedJobsMapper;
import com.example.carrercrafter.repository.JobsRepository;
import com.example.carrercrafter.repository.JobSeekerRepository;
import com.example.carrercrafter.repository.SavedJobsRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class SavedJobsServiceImpl implements SavedJobsService {

    @Autowired
    private SavedJobsRepository savedJobsRepo;

    @Autowired
    private JobSeekerRepository jobSeekerRepo;

    @Autowired
    private JobsRepository jobsRepo;
    
    
    @PersistenceContext
    private EntityManager entityManager;
   


    @Override
    public List<SavedJobsDto> getAll() {
        List<SavedJobs> list = savedJobsRepo.findAll();
        List<SavedJobsDto> dtos = new ArrayList<>();
        for (SavedJobs s : list) {
            dtos.add(SavedJobsMapper.mapToDto(s));
        }
        return dtos;
    }

    @Override
    public SavedJobsDto getById(int id) {
        Optional<SavedJobs> opt = savedJobsRepo.findById(id);
        return opt.map(SavedJobsMapper::mapToDto).orElse(null);
    }

    @Override
    public SavedJobsDto updateSavedJob(int id, SavedJobsDto dto) {
        Optional<SavedJobs> opt = savedJobsRepo.findById(id);
        if (opt.isPresent()) {
            SavedJobs existing = opt.get();

            existing.setSavedDate(dto.getSavedDate());

            JobSeeker seeker = jobSeekerRepo.findById(dto.getJobSeekerId()).orElse(null);
            Jobs job = jobsRepo.findById(dto.getJobId()).orElse(null);

            existing.setJobSeeker(seeker);
            existing.setJob(job);

            SavedJobs updated = savedJobsRepo.save(existing);
            return SavedJobsMapper.mapToDto(updated);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        savedJobsRepo.deleteById(id);
    }
    
    
    @Override
    public List<SavedJobsDto> getByJobSeeker(int seekerId) {
        List<SavedJobs> list = savedJobsRepo.findByJobSeeker_SeekerId(seekerId);
        System.out.println(" DB returned " + list.size() + " saved jobs for seeker " + seekerId);

        for (SavedJobs s : list) {
            System.out.println(" Job ID: " + s.getJob().getJobId() +
                               ", Company: " + s.getJob().getEmployer().getCompanyName());
        }

        List<SavedJobsDto> dtos = new ArrayList<>();
        for (SavedJobs saved : list) {
            dtos.add(SavedJobsMapper.mapToDto(saved));
        }
        return dtos;
    }

    
    
    
    
    @Override
    @Transactional
    public SavedJobsDto save(SavedJobsDto dto) {
        System.out.println("==========  SAVED JOB REQUEST ==========");
        System.out.println("Incoming SavedJobsDto: jobId=" + dto.getJobId() + ", seekerId=" + dto.getJobSeekerId());

        // Step 1: Fetch Job Seeker
        JobSeeker seeker = jobSeekerRepo.findById(dto.getJobSeekerId())
                .orElseThrow(() -> new RuntimeException(" JobSeeker not found with ID: " + dto.getJobSeekerId()));

        // Step 2: Fetch Job
        Jobs job = jobsRepo.findById(dto.getJobId())
                .orElseThrow(() -> new RuntimeException(" Job not found with ID: " + dto.getJobId()));

        //  Step 3: Check for duplicates
        boolean alreadySaved = savedJobsRepo.existsByJobAndJobSeeker(job, seeker);
        if (alreadySaved) {
            System.out.println(" Duplicate: Job already saved by seeker.");
            throw new RuntimeException(" You have already saved this job.");
        }

        // Step 4: Map and Save
        SavedJobs saved = SavedJobsMapper.mapToEntity(dto, seeker, job);
        saved.setSavedDate(LocalDate.now());

        SavedJobs savedEntity = savedJobsRepo.save(saved);
        entityManager.flush(); // force DB write

        System.out.println(" Saved job successfully: Job ID = " + job.getJobId() + ", Seeker ID = " + seeker.getSeekerId());
        return SavedJobsMapper.mapToDto(savedEntity);
    }



}
