package entity;

import java.time.LocalDateTime;

public class InterviewSchedule {
    private int id;
    private int applicationId;
    private LocalDateTime interviewDate;
    private String mode;
    private String status;

    public InterviewSchedule() {}

    public InterviewSchedule(int id, int applicationId, LocalDateTime interviewDate, String mode, String status) {
        this.id = id;
        this.applicationId = applicationId;
        this.interviewDate = interviewDate;
        this.mode = mode;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getApplicationId() { return applicationId; }
    public void setApplicationId(int applicationId) { this.applicationId = applicationId; }

    public LocalDateTime getInterviewDate() { return interviewDate; }
    public void setInterviewDate(LocalDateTime interviewDate) { this.interviewDate = interviewDate; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "InterviewSchedule{" +
                "id=" + id +
                ", applicationId=" + applicationId +
                ", interviewDate=" + interviewDate +
                ", mode='" + mode + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
