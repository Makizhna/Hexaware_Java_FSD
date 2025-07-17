package com.example.carrercrafter.mapper;

import com.example.carrercrafter.dto.InterviewScheduleDto;
import com.example.carrercrafter.entities.Applications;
import com.example.carrercrafter.entities.InterviewSchedule;

public class InterviewScheduleMapper {

  
    public static InterviewScheduleDto mapToDto(InterviewSchedule schedule) {
        if (schedule == null || schedule.getApplication() == null) {
            System.out.println(" Skipping interview with ID " + (schedule != null ? schedule.getId() : "null") + " due "
            		+ "to null application");
            return null;
        }

        InterviewScheduleDto dto = new InterviewScheduleDto();
        dto.setId(schedule.getId());
        dto.setApplicationId(schedule.getApplication().getId());
        dto.setInterviewDate(schedule.getInterviewDate());
        dto.setMode(schedule.getMode());
        dto.setStatus(schedule.getStatus());
        dto.setMeetingLink(schedule.getMeetingLink());
        dto.setCandidateName(schedule.getApplication().getJobSeeker().getName());
        dto.setRescheduleRequest(schedule.getRescheduleRequest());
        dto.setCancelReason(schedule.getCancelReason());
        
        if (schedule.getApplication().getJob() != null) {
            dto.setJobTitle(schedule.getApplication().getJob().getTitle());
            if (schedule.getApplication().getJob().getEmployer() != null) {
                dto.setCompanyName(schedule.getApplication().getJob().getEmployer().getCompanyName());
            }
        }
        dto.setApplicationStatus(schedule.getApplication().getStatus());

        return dto;
    }


    public static InterviewSchedule mapToEntity(InterviewScheduleDto dto, Applications application) {
        if (dto == null) return null;

        InterviewSchedule schedule = new InterviewSchedule();
        schedule.setId(dto.getId());
        schedule.setApplication(application);
        schedule.setInterviewDate(dto.getInterviewDate());
        schedule.setMode(dto.getMode());
        schedule.setMeetingLink(dto.getMeetingLink());
        schedule.setStatus(dto.getStatus());
        schedule.setRescheduleRequest(dto.getRescheduleRequest());
        schedule.setCancelReason(dto.getCancelReason());
        return schedule;
    }
}
