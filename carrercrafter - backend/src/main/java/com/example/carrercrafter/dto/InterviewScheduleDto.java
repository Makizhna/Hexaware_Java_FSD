package com.example.carrercrafter.dto;

import java.time.LocalDateTime;

import com.example.carrercrafter.enums.ApplicationStatus;
import com.example.carrercrafter.enums.InterviewStatus;
import com.example.carrercrafter.enums.InterviewType;

public class InterviewScheduleDto {
	
	private int id;
    private int applicationId;
    private LocalDateTime interviewDate;
    private InterviewType mode;
    private InterviewStatus status;
    private String meetingLink;
    private String candidateName; // only for display in frontend
    private String rescheduleRequest;
    private String cancelReason;
    private String jobTitle;
    private String companyName;
    private ApplicationStatus applicationStatus;




    
    
	public InterviewScheduleDto() {
		super();
		// TODO Auto-generated constructor stub
	}

    

	


	public InterviewScheduleDto(int id, int applicationId, LocalDateTime interviewDate, InterviewType mode,
			InterviewStatus status, String meetingLink, String candidateName, String rescheduleRequest,
			String cancelReason, String jobTitle, String companyName, ApplicationStatus applicationStatus) {
		super();
		this.id = id;
		this.applicationId = applicationId;
		this.interviewDate = interviewDate;
		this.mode = mode;
		this.status = status;
		this.meetingLink = meetingLink;
		this.candidateName = candidateName;
		this.rescheduleRequest = rescheduleRequest;
		this.cancelReason = cancelReason;
		this.jobTitle = jobTitle;
		this.companyName = companyName;
		this.applicationStatus = applicationStatus;
	}






	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getApplicationId() {
		return applicationId;
	}



	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}



	public LocalDateTime getInterviewDate() {
		return interviewDate;
	}



	public void setInterviewDate(LocalDateTime interviewDate) {
		this.interviewDate = interviewDate;
	}



	public InterviewType getMode() {
		return mode;
	}



	public void setMode(InterviewType mode) {
		this.mode = mode;
	}



	public InterviewStatus getStatus() {
		return status;
	}



	public void setStatus(InterviewStatus status) {
		this.status = status;
	}




	public String getMeetingLink() {
		return meetingLink;
	}




	public void setMeetingLink(String meetingLink) {
		this.meetingLink = meetingLink;
	}




	public String getCandidateName() {
		return candidateName;
	}




	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getRescheduleRequest() {
	    return rescheduleRequest;
	}

	public void setRescheduleRequest(String rescheduleRequest) {
	    this.rescheduleRequest = rescheduleRequest;
	}

	public String getCancelReason() {
	    return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
	    this.cancelReason = cancelReason;
	}
	
	


	public String getJobTitle() {
		return jobTitle;
	}



	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}



	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}






	@Override
	public String toString() {
		return "InterviewScheduleDto [id=" + id + ", applicationId=" + applicationId + ", interviewDate="
				+ interviewDate + ", mode=" + mode + ", status=" + status + ", meetingLink=" + meetingLink
				+ ", candidateName=" + candidateName + ", rescheduleRequest=" + rescheduleRequest + ", cancelReason="
				+ cancelReason + ", jobTitle=" + jobTitle + ", companyName=" + companyName + ", applicationStatus="
				+ applicationStatus + "]";
	}
	
	





	
}
