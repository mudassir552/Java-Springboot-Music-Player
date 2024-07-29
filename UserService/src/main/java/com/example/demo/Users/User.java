package com.example.demo.Users;
import jakarta.persistence.JoinColumn;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.stereotype.Component;

import com.example.demo.Roles.Role;
import com.fasterxml.jackson.databind.util.StdConverter;

import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name="user")
@Cacheable
public class User {

	
	@jakarta.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	@Column(name="name",nullable=false)
	private String name;
    
	@Column(nullable=false)
	private String Email;
	
	@Lob
    @Column(name = "profile_image", nullable = true, columnDefinition="LONGBLOB")
	private byte[] Profileimage;
	

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id",nullable=false)
	private List<Role> role;
	                                                                                        
 
   private String primaryUserRole;





@Column(nullable=false)
	private String Password;




public User(Long id, String name, String email, byte[] profileimage, List<Role> role, String password) {
	super();
	Id = id;
	this.name = name;
	Email = email;
	Profileimage = profileimage;
	this.role = role;
	Password = password;
}
	
	
	public User() {
		
	}
	
	
	
	public String getEmail() {
		return Email;
	}



	public void setEmail(String email) {
		Email = email;
	}



	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public List<Role> getRole() {
		return role;
	}



	public void setRole(List<Role> role) {
		this.role = role;
	}


	public void setRole(String role) {
		this.primaryUserRole = role;
	}

	public byte[] getProfileimage() {
		return Profileimage;
	}


	public void setProfileimage(byte[] profileimage) {
		Profileimage = profileimage;
	}
	
	
	


	
	
}
