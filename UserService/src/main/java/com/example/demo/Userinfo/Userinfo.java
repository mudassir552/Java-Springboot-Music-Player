package com.example.demo.Userinfo;

import com.example.demo.Users.User;


import java.util.Collection;
import java.util.stream.Collectors;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class Userinfo implements UserDetails{

	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public Userinfo() {
		
	}
  
	public Userinfo( User user) {
		
	 
	  this.username=user.getName();
	  System.out.println(user.getName());
	 this.password=user.getPassword();
	
	 this.authorities=user.getRole().stream().map(r -> new SimpleGrantedAuthority(r.getRole().toString().toUpperCase())).collect(Collectors.toList());
	

	 
		
	}
	 
   
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		 
		return authorities ;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
    
}
