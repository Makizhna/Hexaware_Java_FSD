package com.example.careercrafter.entity;

import jakarta.persistence.*;

@Entity
public class Employers {

    @Id
    private int userId;

    private String name;
    private String position;
    private String companyName;
    private String location;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private Users user;

    public Employers() {}

    public Employers(String name, String position, String companyName, String location, Users user) {
        this.name = name;
        this.position = position;
        this.companyName = companyName;
        this.location = location;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
        this.userId = user.getId(); 
    }

    @Override
    public String toString() {
        return "Employers{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", companyName='" + companyName + '\'' +
                ", location='" + location + '\'' +
                ", user=" + user +
                '}';
    }
}
