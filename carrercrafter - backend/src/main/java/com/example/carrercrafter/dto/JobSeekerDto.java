package com.example.carrercrafter.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;

public class JobSeekerDto {

	private int seekerId;
    private String name;
    
    @NotBlank(message = "Education is required")
    private String education;

    @NotBlank(message = "Skills are required")
    private String skills;

    @NotBlank(message = "Experience is required")
    private String experience;
    
    private String phone;
    private LocalDate dob;
    private String location;
    
    
    @URL(message = "LinkedIn URL is not valid")
    private String linkedinUrl;
    
    @URL(message = "GitHub URL is not valid")
    private String githubUrl;

    private UserDto user;
    private JobSeekerResumeDto jobSeekerResume;
    
    
	public JobSeekerDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public JobSeekerDto(int seekerId, String name, String education, String skills, String experience, String phone,
			LocalDate dob, String location, String linkedinUrl, String githubUrl, UserDto user,
			JobSeekerResumeDto jobSeekerResume) {
		super();
		this.seekerId = seekerId;
		this.name = name;
		this.education = education;
		this.skills = skills;
		this.experience = experience;
		this.phone = phone;
		this.dob = dob;
		this.location = location;
		this.linkedinUrl = linkedinUrl;
		this.githubUrl = githubUrl;
		this.user = user;
		this.jobSeekerResume = jobSeekerResume;
	}


	public int getSeekerId() {
		return seekerId;
	}


	public void setSeekerId(int seekerId) {
		this.seekerId = seekerId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEducation() {
		return education;
	}


	public void setEducation(String education) {
		this.education = education;
	}


	public String getSkills() {
		return skills;
	}


	public void setSkills(String skills) {
		this.skills = skills;
	}


	public String getExperience() {
		return experience;
	}


	public void setExperience(String experience) {
		this.experience = experience;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public LocalDate getDob() {
		return dob;
	}


	public void setDob(LocalDate dob) {
		this.dob = dob;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getLinkedinUrl() {
		return linkedinUrl;
	}


	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
	}


	public String getGithubUrl() {
		return githubUrl;
	}


	public void setGithubUrl(String githubUrl) {
		this.githubUrl = githubUrl;
	}


	public UserDto getUser() {
		return user;
	}


	public void setUser(UserDto user) {
		this.user = user;
	}


	public JobSeekerResumeDto getJobSeekerResume() {
		return jobSeekerResume;
	}


	public void setJobSeekerResume(JobSeekerResumeDto jobSeekerResume) {
		this.jobSeekerResume = jobSeekerResume;
	}


	@Override
	public String toString() {
		return "JobSeekerDto [seekerId=" + seekerId + ", name=" + name + ", education=" + education + ", skills="
				+ skills + ", experience=" + experience + ", phone=" + phone + ", dob=" + dob + ", location=" + location
				+ ", linkedinUrl=" + linkedinUrl + ", githubUrl=" + githubUrl + ", user=" + user + ", jobSeekerResume="
				+ jobSeekerResume + "]";
	}
	
	
	
	
    
    
    
    
}
