package com.example.careercrafter.entity;

import jakarta.persistence.*;

@Entity
public class Applications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Jobs job;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id")
    private JobSeekers jobSeeker;

    private String resumeUrl;
    private String status;
    
    
    public Applications() {};
    
    public Applications(Jobs job, JobSeekers jobSeeker, String resumeUrl, String status) {
        this.job = job;
        this.jobSeeker = jobSeeker;
        this.resumeUrl = resumeUrl;
        this.status = status;
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

    public JobSeekers getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeekers jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Applications{" +
                "id=" + id +
                ", job=" + job +
                ", jobSeeker=" + jobSeeker +
                ", resumeUrl='" + resumeUrl + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
