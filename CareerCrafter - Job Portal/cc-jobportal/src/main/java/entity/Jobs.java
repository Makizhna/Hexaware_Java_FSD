package entity;

public class Jobs {
    private int jobId;
    private String title;
    private String description;
    private String location;
    private int postedBy;

    public Jobs() {}

    public Jobs (int jobId, String title, String description, String location, int postedBy) {
        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.postedBy = postedBy;
    }

    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getPostedBy() { return postedBy; }
    public void setPostedBy(int postedBy) { this.postedBy = postedBy; }

    @Override
    public String toString() {
        return "Job{" + "jobId=" + jobId + ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", postedBy=" + postedBy + '}';
    }
}
