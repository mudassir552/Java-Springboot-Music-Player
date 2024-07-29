package com.example.demo.Controllers;

import java.util.List;


import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.demo.Roles.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Authrequest {
	
	
	@NotEmpty(message = "Username is mandatory")
	private String username; 
	 
	@NotEmpty(message = "password is mandatory")
	private String password;
	 
	@NotEmpty(message = "Email is mandatory")
	private String email ;
	
	@NotEmpty(message = "please select a role")
	private List<Role> roles;
	
	 public Authrequest() {
		 
	 }
	 
	public Authrequest(String username, String password, String email, List<Role> roles) {
		super();
		this.username=username;
		this.password=password;
		this.email = email;
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
	public String getemail() {
		return email;
	}
	public void setemail(String email) {
		this.email = email;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(  List<Role> roles) {
		this.roles = roles;
	}

	public String getpassword() {
		
		return password;
	}
	
	
  
	
    
    
    
}
