package com.example.carrercrafter.entities;
import jakarta.persistence.*;

@Entity
public class Employer{
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;
    
    private String name;
    private String position;
    private String workEmail;
	private String linkedinUrl;
    private String companyName;
    private String location;
    
    private String companyWebsite;  
    
    @Column(length = 1000)
    private String companyBio;    

    @OneToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private boolean active = true;


    public Employer () {}

	

	public Employer(int employeeId, String name, String position, String workEmail, String linkedinUrl,
			String companyName, String location, String companyWebsite, String companyBio, User user, boolean active) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.position = position;
		this.workEmail = workEmail;
		this.linkedinUrl = linkedinUrl;
		this.companyName = companyName;
		this.location = location;
		this.companyWebsite = companyWebsite;
		this.companyBio = companyBio;
		this.user = user;
		this.active = active;
	}



	public int getEmployeeId() {
		return employeeId;
	}



	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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



	public String getWorkEmail() {
		return workEmail;
	}



	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}



	public String getLinkedinUrl() {
		return linkedinUrl;
	}



	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
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



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}



	public String getCompanyWebsite() {
		return companyWebsite;
	}



	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}



	public String getCompanyBio() {
		return companyBio;
	}



	public void setCompanyBio(String companyBio) {
		this.companyBio = companyBio;
	}



	@Override
	public String toString() {
		return "Employer [employeeId=" + employeeId + ", name=" + name + ", position=" + position + ", workEmail="
				+ workEmail + ", linkedinUrl=" + linkedinUrl + ", companyName=" + companyName + ", location=" + location
				+ ", companyWebsite=" + companyWebsite + ", companyBio=" + companyBio + ", user=" + user + ", active="
				+ active + "]";
	}



}
