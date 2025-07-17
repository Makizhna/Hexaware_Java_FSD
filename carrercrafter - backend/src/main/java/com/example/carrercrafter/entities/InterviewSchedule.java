package com.example.carrercrafter.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.example.carrercrafter.enums.InterviewStatus;
import com.example.carrercrafter.enums.InterviewType;

@Entity
public class InterviewSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "application_id")
    private Applications application;

    private LocalDateTime interviewDate;
    
    @Enumerated(EnumType.STRING)
    private InterviewType mode; 

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;
    
    @Column(name = "meeting_link")
    private String meetingLink;
    
    @Column(length = 1000)
    private String rescheduleRequest;

    @Column(length = 1000)
    private String cancelReason;



	public InterviewSchedule() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public InterviewSchedule(int id, Applications application, LocalDateTime interviewDate, InterviewType mode,
			InterviewStatus status, String meetingLink, String rescheduleRequest, String cancelReason) {
		super();
		this.id = id;
		this.application = application;
		this.interviewDate = interviewDate;
		this.mode = mode;
		this.status = status;
		this.meetingLink = meetingLink;
		this.rescheduleRequest = rescheduleRequest;
		this.cancelReason = cancelReason;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Applications getApplication() {
		return application;
	}


	public void setApplication(Applications application) {
		this.application = application;
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



	@Override
	public String toString() {
		return "InterviewSchedule [id=" + id + ", application=" + application + ", interviewDate=" + interviewDate
				+ ", mode=" + mode + ", status=" + status + ", meetingLink=" + meetingLink + ", rescheduleRequest="
				+ rescheduleRequest + ", cancelReason=" + cancelReason + "]";
	}
	
	
}
