package entity;

public class SavedJobs {
    private int id;
    private int jobSeekerId;
    private int jobId;

    public SavedJobs () {}

    public SavedJobs (int id, int jobSeekerId, int jobId) {
        this.id = id;
        this.jobSeekerId = jobSeekerId;
        this.jobId = jobId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getJobSeekerId() { return jobSeekerId; }
    public void setJobSeekerId(int jobSeekerId) { this.jobSeekerId = jobSeekerId; }

    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

    @Override
    public String toString() {
        return "SavedJob{" +
                "id=" + id +
                ", jobSeekerId=" + jobSeekerId +
                ", jobId=" + jobId +
                '}';
    }
}
