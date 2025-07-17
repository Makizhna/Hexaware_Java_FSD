package com.example.carrercrafter.dto;


public class AuthResponse {
	
    private String token;
    private String role;
    private int userId;
    
    

    public AuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuthResponse(String token,  String role, int userId) {
		super();
		this.token = token;
		this.role = role;	
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "AuthResponse [token=" + token + ", role=" + role + ", userId=" + userId + "]";
	}

	
		

}
