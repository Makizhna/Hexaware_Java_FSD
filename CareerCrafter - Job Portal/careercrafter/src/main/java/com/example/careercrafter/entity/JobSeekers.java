package com.example.careercrafter.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class JobSeekers {

    @Id
    private int userId;

    private String name;
    private String education;
    private String skills;
    private String experience;
    private String phone;
    private String resumeUrl;
    private LocalDate dob;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private Users user;

    public JobSeekers() {}

    public JobSeekers(String name, String education, String skills, String experience,
                      String phone, String resumeUrl, LocalDate dob, Users user) {
        this.name = name;
        this.education = education;
        this.skills = skills;
        this.experience = experience;
        this.phone = phone;
        this.resumeUrl = resumeUrl;
        this.dob = dob;
        this.user = user;
        this.userId = user.getId(); 
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
        this.userId = user.getId(); // Ensure consistency
    }

    @Override
    public String toString() {
        return "JobSeekers{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", education='" + education + '\'' +
                ", skills='" + skills + '\'' +
                ", experience='" + experience + '\'' +
                ", phone='" + phone + '\'' +
                ", resumeUrl='" + resumeUrl + '\'' +
                ", dob=" + dob +
                ", user=" + user +
                '}';
    }
}
