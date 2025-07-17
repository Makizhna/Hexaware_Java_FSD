package com.example.carrercrafter.entities;

import com.example.carrercrafter.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private int id;
	private String name;
	
	
	@NotBlank(message = "Email is required")
	@Column(nullable = false, unique = true)
    private String email;
	
	@NotBlank(message = "Password is required")
	@Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    private UserRole role;               //JOB_SEEKER, EMPLOYER

    public User() {}

	
	

	public User(int id, String name, @NotBlank(message = "Email is required") String email,
			@NotBlank(message = "Password is required") String password, UserRole role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}




	public User(String email, String password, UserRole role) {
	    this.email = email;
	    this.password = password;
	    this.role = role;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public UserRole getRole() {
		return role;
	}


	public void setRole(UserRole role) {
		this.role = role;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ "]";
	}
	
	
	

    
}
