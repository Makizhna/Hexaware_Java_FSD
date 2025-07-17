package com.example.carrercrafter.ServiceImpl;

import com.example.carrercrafter.Service.InterviewScheduleService;
import com.example.carrercrafter.dto.InterviewScheduleDto;
import com.example.carrercrafter.entities.Applications;
import com.example.carrercrafter.entities.InterviewSchedule;
import com.example.carrercrafter.enums.ApplicationStatus;
import com.example.carrercrafter.enums.InterviewStatus;
import com.example.carrercrafter.mapper.InterviewScheduleMapper;
import com.example.carrercrafter.repository.ApplicationsRepository;
import com.example.carrercrafter.repository.InterviewScheduleRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InterviewScheduleServiceImpl implements InterviewScheduleService {

    @Autowired
    private InterviewScheduleRepository interviewScheduleRepo;

    @Autowired
    private ApplicationsRepository applicationsRepo;

    @Override
    public InterviewScheduleDto saveSchedule(InterviewScheduleDto dto) {
        // Check if an interview is already scheduled for this application
        boolean exists = interviewScheduleRepo.existsByApplication_Id(dto.getApplicationId());
        if (exists) {
            throw new RuntimeException("Interview already scheduled for this application.");
        }

        Applications app = applicationsRepo.findById(dto.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        //Save Interview
        InterviewSchedule schedule = InterviewScheduleMapper.mapToEntity(dto, app);
        InterviewSchedule saved = interviewScheduleRepo.save(schedule);
        
        // Update application status
        app.setStatus(ApplicationStatus.INTERVIEW_SCHEDULED);
        applicationsRepo.save(app); // Save updated application

        
        return InterviewScheduleMapper.mapToDto(saved);
    }


    @Override
    public List<InterviewScheduleDto> getAllSchedules() {
        List<InterviewSchedule> schedules = interviewScheduleRepo.findAll();
        List<InterviewScheduleDto> dtos = new ArrayList<>();
        for (InterviewSchedule s : schedules) {
            dtos.add(InterviewScheduleMapper.mapToDto(s));
        }
        return dtos;
    }

    @Override
    public InterviewScheduleDto getScheduleById(int id) {
        Optional<InterviewSchedule> opt = interviewScheduleRepo.findById(id);
        return opt.map(InterviewScheduleMapper::mapToDto).orElse(null);
    }

    
    
    @Override
    public InterviewScheduleDto updateSchedule(int id, InterviewScheduleDto dto) {
        Optional<InterviewSchedule> opt = interviewScheduleRepo.findById(id);
        if (opt.isPresent()) {
            InterviewSchedule schedule = opt.get();

            Applications app = applicationsRepo.findById(dto.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

            //  MUST set application properly
            schedule.setApplication(app);
            schedule.setInterviewDate(dto.getInterviewDate());
            schedule.setMode(dto.getMode());
            schedule.setStatus(dto.getStatus());
            schedule.setMeetingLink(dto.getMeetingLink());
            schedule.setCancelReason(dto.getCancelReason());
            schedule.setRescheduleRequest(dto.getRescheduleRequest());

            InterviewSchedule updated = interviewScheduleRepo.save(schedule);
            return InterviewScheduleMapper.mapToDto(updated);
        }
        return null;
    }

    
    
    @Override
    public List<InterviewScheduleDto> getByJobSeekerId(int seekerId) {
        List<InterviewSchedule> schedules = interviewScheduleRepo.findByApplication_JobSeeker_SeekerId(seekerId);
        return schedules.stream()
        		.filter(s -> s.getApplication().getStatus() != ApplicationStatus.REJECTED) // ✅ filter rejected applications
                .map(InterviewScheduleMapper::mapToDto)
                .collect(Collectors.toList());
    }


    @Override
    public void deleteSchedule(int id) {
        interviewScheduleRepo.deleteById(id);
    }
    
    
    
    
    
    @Override
    public void requestReschedule(int id, String message) {
        InterviewSchedule schedule = interviewScheduleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        if (schedule.getStatus() == InterviewStatus.RESCHEDULE_REQUESTED) {
            throw new RuntimeException("Reschedule request already submitted.");
        }
        if (schedule.getStatus() == InterviewStatus.RESCHEDULE_REJECTED) {
            throw new RuntimeException("Previous reschedule request was rejected. Cannot send another.");
        }

        schedule.setRescheduleRequest(message);
        schedule.setStatus(InterviewStatus.RESCHEDULE_REQUESTED);  
        interviewScheduleRepo.save(schedule);
    }

    
   

    @Override
    public void cancelInterview(int id, String reason) {
        InterviewSchedule schedule = interviewScheduleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));
        schedule.setCancelReason(reason);
        schedule.setStatus(InterviewStatus.CANCELLED);
        interviewScheduleRepo.save(schedule);
    }
    
    
    @Override
    public void approveReschedule(int id) {
        InterviewSchedule schedule = interviewScheduleRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Interview not found"));

        // Clear the request and optionally set a new status if needed
        schedule.setRescheduleRequest(null); // Clear request
        interviewScheduleRepo.save(schedule);
    }

    @Override
    public void rejectReschedule(int id) {
        InterviewSchedule schedule = interviewScheduleRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Interview not found"));

        schedule.setRescheduleRequest(null);
        schedule.setStatus(InterviewStatus.RESCHEDULE_REJECTED); 
        interviewScheduleRepo.save(schedule);
    }


    
    @Override
    public InterviewScheduleDto getByApplicationId(int applicationId) {
        InterviewSchedule schedule = interviewScheduleRepo.findByApplication_Id(applicationId)
                .orElseThrow(() -> new RuntimeException("Interview not found for application ID: " + applicationId));
        return InterviewScheduleMapper.mapToDto(schedule);
    }


}
