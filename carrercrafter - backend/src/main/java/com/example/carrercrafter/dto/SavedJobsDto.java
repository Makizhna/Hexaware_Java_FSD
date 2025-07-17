package com.example.carrercrafter.dto;

import java.time.LocalDate;

public class SavedJobsDto {
	
	private int id;
    private int jobSeekerId;
    private int jobId;
    private String jobTitle; 
    private LocalDate savedDate;
    private String companyName;
    private String applicationDeadline;

    
	public SavedJobsDto() {
		super();
		// TODO Auto-generated constructor stub
	}



	public SavedJobsDto(int id, int jobSeekerId, int jobId, String jobTitle, LocalDate savedDate, String companyName,
			String applicationDeadline) {
		super();
		this.id = id;
		this.jobSeekerId = jobSeekerId;
		this.jobId = jobId;
		this.jobTitle = jobTitle;
		this.savedDate = savedDate;
		this.companyName = companyName;
		this.applicationDeadline = applicationDeadline;
	}




	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getJobSeekerId() {
		return jobSeekerId;
	}


	public void setJobSeekerId(int jobSeekerId) {
		this.jobSeekerId = jobSeekerId;
	}


	public int getJobId() {
		return jobId;
	}


	public void setJobId(int jobId) {
		this.jobId = jobId;
	}


	public LocalDate getSavedDate() {
		return savedDate;
	}


	public void setSavedDate(LocalDate savedDate) {
		this.savedDate = savedDate;
	}

	

	public String getCompanyName() {
		return companyName;
	}




	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}




	public String getApplicationDeadline() {
		return applicationDeadline;
	}




	public void setApplicationDeadline(String applicationDeadline) {
		this.applicationDeadline = applicationDeadline;
	}



	public String getJobTitle() {
		return jobTitle;
	}



	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}



	@Override
	public String toString() {
		return "SavedJobsDto [id=" + id + ", jobSeekerId=" + jobSeekerId + ", jobId=" + jobId + ", jobTitle=" + jobTitle
				+ ", savedDate=" + savedDate + ", companyName=" + companyName + ", applicationDeadline="
				+ applicationDeadline + "]";
	}


    
	
    

}
