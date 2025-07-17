package com.example.carrercrafter.Service;
import com.example.carrercrafter.dto.ApplicationsDto;
//import com.example.carrercrafter.entities.Applications;

import java.util.List;


public interface ApplicationsService {

    
    ApplicationsDto saveApplication(ApplicationsDto applicationDto);
    List<ApplicationsDto> getAllApplications();
    ApplicationsDto getApplicationById(int id);
    ApplicationsDto updateApplication(int id, ApplicationsDto applicationDto);
    void deleteApplication(int id);
    List<ApplicationsDto> getApplicationsByJobSeekerId(int seekerId);
    List<ApplicationsDto> getApplicationsByJobId(int jobId);
    List<ApplicationsDto> getApplicationsByEmployerId(int employerId);
    ApplicationsDto updateApplicationStatus(int applicationId, String status);

}
