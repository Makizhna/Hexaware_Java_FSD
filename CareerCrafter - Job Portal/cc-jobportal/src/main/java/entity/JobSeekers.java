package entity;

import java.time.LocalDate;

public class JobSeekers{
    private int userId;
    private String name;
    private LocalDate dob;
    private String phone;
    private String education;
    private String experience;
    private String skills;
    private String resumeUrl;

    public JobSeekers() {}

    public JobSeekers(int userId, String name, LocalDate dob, String phone,
                     String education, String experience, String skills, String resumeUrl) {
        this.userId = userId;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.education = education;
        this.experience = experience;
        this.skills = skills;
        this.resumeUrl = resumeUrl;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }

    @Override
    public String toString() {
        return "JobSeeker{" + "userId=" + userId + ", name='" + name + '\'' +
                ", dob=" + dob + ", phone='" + phone + '\'' +
                ", education='" + education + '\'' +
                ", experience='" + experience + '\'' +
                ", skills='" + skills + '\'' +
                ", resumeUrl='" + resumeUrl + '\'' + '}';
    }
}
