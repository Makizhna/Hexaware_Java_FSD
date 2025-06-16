package com.example.careercrafter.entity;

import jakarta.persistence.*;

@Entity
public class SavedJobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id")
    private JobSeekers jobSeeker;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Jobs job;
    
    public SavedJobs() {};

    public SavedJobs(JobSeekers jobSeeker, Jobs job) {
        this.jobSeeker = jobSeeker;
        this.job = job;
    }


	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JobSeekers getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeekers jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public Jobs getJob() {
        return job;
    }

    public void setJob(Jobs job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "SavedJobs{" +
                "id=" + id +
                ", jobSeeker=" + jobSeeker +
                ", job=" + job +
                '}';
    }
}
