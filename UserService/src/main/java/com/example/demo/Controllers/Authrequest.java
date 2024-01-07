package com.example.demo.Controllers;

import java.util.List;

import org.springframework.lang.NonNull;

import com.example.demo.Roles.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Authrequest {
	
	
	 @NonNull
	private String username; 
	 @NonNull
	private String password;
	 
	 @NonNull
	private String Email ;
	 @NonNull
	private List<Role>roles;
	
	 public Authrequest() {
		 
	 }
	 
	public Authrequest(String username, String password, String email, List<Role> roles) {
		super();
		this.username=username;
		this.password=password;
		Email = email;
		this.roles=roles;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
		
		
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	
  
	
    
    
    
}
