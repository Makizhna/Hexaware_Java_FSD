package com.example.careercrafter.entity;

import jakarta.persistence.*;

@Entity
public class Employers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;

    private String name;
    private String position;
    private String companyName;
    private String location;

    @OneToOne
    @JoinColumn(name = "id")
    private Users user;

    public Employers() {}

	public Employers(int employeeId, String name, String position, String companyName, String location, Users user) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.position = position;
		this.companyName = companyName;
		this.location = location;
		this.user = user;
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
	}

	@Override
	public String toString() {
		return "Employers [userId=" + employeeId + ", name=" + name + ", position=" + position + ", companyName="
				+ companyName + ", location=" + location + ", user=" + user + "]";
	}

}
