package com.example.carrercrafter.entities;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class SavedJobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private JobSeeker jobSeeker;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Jobs job;
    
    private LocalDate savedDate;
    
    public SavedJobs() {}

	public SavedJobs(int id, JobSeeker jobSeeker, Jobs job, LocalDate savedDate) {
		super();
		this.id = id;
		this.jobSeeker = jobSeeker;
		this.job = job;
		this.savedDate = savedDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}

	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}

	public Jobs getJob() {
		return job;
	}

	public void setJob(Jobs job) {
		this.job = job;
	}

	public LocalDate getSavedDate() {
		return savedDate;
	}

	public void setSavedDate(LocalDate savedDate) {
		this.savedDate = savedDate;
	}

	@Override
	public String toString() {
		return "SavedJobs [id=" + id + ", jobSeeker=" + jobSeeker + ", job=" + job + ", savedDate=" + savedDate + "]";
	};

    
}
