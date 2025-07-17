package com.example.carrercrafter.Service;
import com.example.carrercrafter.dto.InterviewScheduleDto;
//import com.example.carrercrafter.entities.InterviewSchedule;

import java.util.List;


public interface InterviewScheduleService {

   
    InterviewScheduleDto saveSchedule(InterviewScheduleDto dto);
    List<InterviewScheduleDto> getAllSchedules();
    InterviewScheduleDto getScheduleById(int id);
    InterviewScheduleDto updateSchedule(int id, InterviewScheduleDto interviewScheduledto);
    void deleteSchedule(int id);
	List<InterviewScheduleDto> getByJobSeekerId(int seekerId);
	void requestReschedule(int id, String message);
	void cancelInterview(int id, String reason);
	void approveReschedule(int id);
	void rejectReschedule(int id);
	InterviewScheduleDto getByApplicationId(int applicationId);


}
