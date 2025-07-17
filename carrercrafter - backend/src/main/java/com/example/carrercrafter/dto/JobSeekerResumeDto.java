package com.example.carrercrafter.dto;

import java.time.LocalDate;

public class JobSeekerResumeDto {
    private int resumeId;
    private String fileName;
    private String fileUrl;
    private LocalDate uploadedAt;
    private int jobSeekerId; // To link resume to JobSeeker

    public JobSeekerResumeDto() {}
    
    

    public JobSeekerResumeDto(int resumeId, String fileName, String fileUrl, LocalDate uploadedAt, int jobSeekerId) {
		super();
		this.resumeId = resumeId;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.uploadedAt = uploadedAt;
		this.jobSeekerId = jobSeekerId;
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

    public int getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(int jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }
}
