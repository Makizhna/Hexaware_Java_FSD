package com.example.carrercrafter.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import com.example.carrercrafter.enums.JobType;
import com.example.carrercrafter.enums.LocationType;

import jakarta.persistence.Column;


public class JobsDto {
	
	private int jobId;
    private String title;
    private String description;
    private String location;
    private LocationType locationType;
    private JobType jobType; 
    private String jobExperience;
    private String qualifications;
    private List<String> skillsRequired; 
    private String salary;
    private LocalDate postedDate;
    
    @Column(nullable = false)
    private LocalDate applicationDeadline;

    @Column(nullable = false)
    private String applicationEmail;
    
    private String applicationInstructions;

    @CreationTimestamp
    private Date createdAt;
    
    //private int employerId;
    private EmployerDto employer;

    private boolean active = true;
    
    

	public JobsDto() {
		super();
		// TODO Auto-generated constructor stub
	}



	public JobsDto(int jobId, String title, String description, String location, LocationType locationType,
			JobType jobType, String jobExperience, String qualifications, List<String> skillsRequired, String salary,
			LocalDate postedDate, LocalDate applicationDeadline, String applicationEmail,
			String applicationInstructions, Date createdAt, EmployerDto employer, boolean active) {
		super();
		this.jobId = jobId;
		this.title = title;
		this.description = description;
		this.location = location;
		this.locationType = locationType;
		this.jobType = jobType;
		this.jobExperience = jobExperience;
		this.qualifications = qualifications;
		this.skillsRequired = skillsRequired;
		this.salary = salary;
		this.postedDate = postedDate;
		this.applicationDeadline = applicationDeadline;
		this.applicationEmail = applicationEmail;
		this.applicationInstructions = applicationInstructions;
		this.createdAt = createdAt;
		this.employer = employer;
		this.active = active;
	}




	public int getJobId() {
		return jobId;
	}



	public void setJobId(int jobId) {
		this.jobId = jobId;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public LocationType getLocationType() {
		return locationType;
	}



	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}



	public JobType getJobType() {
		return jobType;
	}



	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}



	public String getJobExperience() {
		return jobExperience;
	}



	public void setJobExperience(String jobExperience) {
		this.jobExperience = jobExperience;
	}



	public String getQualifications() {
		return qualifications;
	}



	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}



	public List<String> getSkillsRequired() {
		return skillsRequired;
	}



	public void setSkillsRequired(List<String> skillsRequired) {
		this.skillsRequired = skillsRequired;
	}



	public String getSalary() {
		return salary;
	}



	public void setSalary(String salary) {
		this.salary = salary;
	}



	public LocalDate getPostedDate() {
		return postedDate;
	}



	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}




	public LocalDate getApplicationDeadline() {
		return applicationDeadline;
	}



	public void setApplicationDeadline(LocalDate applicationDeadline) {
		this.applicationDeadline = applicationDeadline;
	}



	public String getApplicationEmail() {
		return applicationEmail;
	}



	public void setApplicationEmail(String applicationEmail) {
		this.applicationEmail = applicationEmail;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}




	public EmployerDto getEmployer() {
		return employer;
	}

	public void setEmployer(EmployerDto employer) {
		this.employer = employer;
	}


	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}



	public String getApplicationInstructions() {
		return applicationInstructions;
	}



	public void setApplicationInstructions(String applicationInstructions) {
		this.applicationInstructions = applicationInstructions;
	}



	@Override
	public String toString() {
		return "JobsDto [jobId=" + jobId + ", title=" + title + ", description=" + description + ", location="
				+ location + ", locationType=" + locationType + ", jobType=" + jobType + ", jobExperience="
				+ jobExperience + ", qualifications=" + qualifications + ", skillsRequired=" + skillsRequired
				+ ", salary=" + salary + ", postedDate=" + postedDate + ", applicationDeadline=" + applicationDeadline
				+ ", applicationEmail=" + applicationEmail + ", applicationInstructions=" + applicationInstructions
				+ ", createdAt=" + createdAt + ", employer=" + employer + ", active=" + active + "]";
	}



}
