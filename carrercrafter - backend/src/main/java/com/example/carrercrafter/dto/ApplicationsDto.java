package com.example.carrercrafter.dto;

import java.time.LocalDate;

import com.example.carrercrafter.enums.ApplicationStatus;

public class ApplicationsDto {

    private int id;
    private int jobId;
    private int jobSeekerId;
    private int resumeId;
    private ApplicationStatus status;
    private LocalDate appliedDate;
    private String jobTitle;
    private String companyName;
    private String resumeUrl; 
    private String coverLetter;
    private String jobSeekerName;



    
	public ApplicationsDto() {
		super();
		// TODO Auto-generated constructor stub
	}

    
	public ApplicationsDto(int id, int jobId, int jobSeekerId, int resumeId, ApplicationStatus status,
	LocalDate appliedDate, String jobTitle, String companyName, String resumeUrl, String coverLetter,String jobSeekerName) {
		super();
		this.id = id;
		this.jobId = jobId;
		this.jobSeekerId = jobSeekerId;
		this.resumeId = resumeId;
		this.status = status;
		this.appliedDate = appliedDate;
		this.jobTitle = jobTitle;
		this.companyName = companyName;
		this.resumeUrl = resumeUrl;
		this.coverLetter = coverLetter;
		this. jobSeekerName = jobSeekerName;
	}


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getJobId() {
		return jobId;
	}



	public void setJobId(int jobId) {
		this.jobId = jobId;
	}



	public int getJobSeekerId() {
		return jobSeekerId;
	}



	public void setJobSeekerId(int jobSeekerId) {
		this.jobSeekerId = jobSeekerId;
	}



	public int getResumeId() {
		return resumeId;
	}



	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}



	public ApplicationStatus getStatus() {
		return status;
	}



	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}



	public LocalDate getAppliedDate() {
		return appliedDate;
	}



	public void setAppliedDate(LocalDate appliedDate) {
		this.appliedDate = appliedDate;
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

	public String getResumeUrl() {
		return resumeUrl;
	}


	public void setResumeUrl(String resumeUrl) {
		this.resumeUrl = resumeUrl;
	}


	public String getCoverLetter() {
		return coverLetter;
	}


	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}


	public String getJobSeekerName() {
		return jobSeekerName;
	}


	public void setJobSeekerName(String jobSeekerName) {
		this.jobSeekerName = jobSeekerName;
	}


	@Override
	public String toString() {
		return "ApplicationsDto [id=" + id + ", jobId=" + jobId + ", jobSeekerId=" + jobSeekerId + ", resumeId="
				+ resumeId + ", status=" + status + ", appliedDate=" + appliedDate + ", jobTitle=" + jobTitle
				+ ", companyName=" + companyName + ", resumeUrl=" + resumeUrl + ", coverLetter=" + coverLetter
				+ ", jobSeekerName=" + jobSeekerName + "]";
	}



    
}
