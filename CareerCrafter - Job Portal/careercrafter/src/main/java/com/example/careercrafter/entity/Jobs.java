package com.example.careercrafter.entity;

import jakarta.persistence.*;

@Entity
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobId;

    private String title;
    private String description;
    private String location;

    @ManyToOne
    @JoinColumn(name = "posted_by")
    private Employers postedBy;
    
    public Jobs() {}

    public Jobs(String title, String description, String location, Employers postedBy) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.postedBy = postedBy;
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

    public Employers getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(Employers postedBy) {
        this.postedBy = postedBy;
    }

    @Override
    public String toString() {
        return "Jobs{" +
                "jobId=" + jobId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", postedBy=" + postedBy +
                '}';
    }
}
