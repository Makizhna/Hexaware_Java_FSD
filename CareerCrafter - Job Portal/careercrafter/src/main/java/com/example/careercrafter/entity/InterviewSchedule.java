package com.example.careercrafter.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class InterviewSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "application_id")
    private Applications application;

    private LocalDateTime interviewDate;
    private String mode;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    public InterviewSchedule(Applications application, LocalDateTime interviewDate, String mode, String status) {
        this.application = application;
        this.interviewDate = interviewDate;
        this.mode = mode;
        this.status = status;
    }


    public Applications getApplication() {
        return application;
    }

    public void setApplication(Applications application) {
        this.application = application;
    }

    public LocalDateTime getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDateTime interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InterviewSchedule{" +
                "id=" + id +
                ", application=" + application +
                ", interviewDate=" + interviewDate +
                ", mode='" + mode + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
