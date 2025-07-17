package com.example.carrercrafter.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class JobSeekerResume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resumeId;

    private String fileName;
    private String fileUrl;
    private LocalDate uploadedAt;

    @OneToOne
    @JoinColumn(name = "seeker_id", referencedColumnName = "seekerId", nullable = false)
    private JobSeeker jobSeeker;

    public JobSeekerResume() {}

    public JobSeekerResume(int resumeId, String fileName, String fileUrl, LocalDate uploadedAt, JobSeeker jobSeeker) {
        this.resumeId = resumeId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.uploadedAt = uploadedAt;
        this.jobSeeker = jobSeeker;
    }

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LocalDate getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDate uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    @Override
    public String toString() {
        return "JobSeekerResume{" +
                "resumeId=" + resumeId +
                ", fileName='" + fileName + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", uploadedAt=" + uploadedAt +
                ", jobSeekerId=" + (jobSeeker != null ? jobSeeker.getSeekerId() : null) +
                '}';
    }
}
