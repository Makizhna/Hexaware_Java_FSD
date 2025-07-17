package com.example.carrercrafter.entities;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.example.carrercrafter.enums.ApplicationStatus;

import jakarta.persistence.*;

@Entity
public class Applications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Jobs job;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private JobSeeker jobSeeker;

    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private JobSeekerResume resume;
   
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.APPLIED;;  //APPLIED, UNDER_REVIEW, SHORTLISTED, INTERVIEW_SCHEDULED, REJECTED, SELECTED
    
    @CreationTimestamp
    private LocalDate appliedDate;
    
    @Column(columnDefinition = "TEXT")
    private String coverLetter;

    
    
    public Applications() {}


	

	public Applications(int id, Jobs job, JobSeeker jobSeeker, JobSeekerResume resume, ApplicationStatus status,
			LocalDate appliedDate, String coverLetter) {
		super();
		this.id = id;
		this.job = job;
		this.jobSeeker = jobSeeker;
		this.resume = resume;
		this.status = status;
		this.appliedDate = appliedDate;
		this.coverLetter = coverLetter;
	}




	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Jobs getJob() {
		return job;
	}


	public void setJob(Jobs job) {
		this.job = job;
	}


	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}


	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}


	public JobSeekerResume getResume() {
		return resume;
	}


	public void setResume(JobSeekerResume resume) {
		this.resume = resume;
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




	public String getCoverLetter() {
		return coverLetter;
	}




	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}




	@Override
	public String toString() {
		return "Applications [id=" + id + ", job=" + job + ", jobSeeker=" + jobSeeker + ", resume=" + resume
				+ ", status=" + status + ", appliedDate=" + appliedDate + ", coverLetter=" + coverLetter + "]";
	}


   
   
}
