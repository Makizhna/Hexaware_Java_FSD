package com.example.carrercrafter.Service;

import java.util.List;

import com.example.carrercrafter.dto.SavedJobsDto;
//import com.example.carrercrafter.entities.SavedJobs;

public interface SavedJobsService {

    SavedJobsDto save(SavedJobsDto savedJobsdto);
    List<SavedJobsDto> getAll();
    SavedJobsDto getById(int id);
    SavedJobsDto updateSavedJob(int id, SavedJobsDto savedJobsdto);
    void delete(int id);
	List<SavedJobsDto> getByJobSeeker(int seekerId);


}
