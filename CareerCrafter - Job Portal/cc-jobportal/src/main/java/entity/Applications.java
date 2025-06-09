package entity;

public class Applications {
    private int id;
    private int jobId;
    private int jobSeekerId;
    private String resumeUrl;
    private String status;

    public Applications () {}

    public Applications (int id, int jobId, int jobSeekerId, String resumeUrl, String status) {
        this.id = id;
        this.jobId = jobId;
        this.jobSeekerId = jobSeekerId;
        this.resumeUrl = resumeUrl;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

    public int getJobSeekerId() { return jobSeekerId; }
    public void setJobSeekerId(int jobSeekerId) { this.jobSeekerId = jobSeekerId; }

    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Application{" + "id=" + id + ", jobId=" + jobId +
                ", jobSeekerId=" + jobSeekerId + ", resumeUrl='" + resumeUrl + '\'' +
                ", status='" + status + '\'' + '}';
    }
}
