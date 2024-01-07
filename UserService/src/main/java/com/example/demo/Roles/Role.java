package com.example.demo.Roles;

import com.example.demo.Users.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="role")
public class Role {
	
	@Column
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long roleid;
	
	 
	@Column
	private String Role;
	
	
	
    
    public Role() {
    	
    }
    
	public Role(Long roleid, String Role) {
		super();
		this.roleid = roleid;
		this.Role =Role;
	}

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	
	public String getRole() {
		return Role;
	}

	public void setRole(String Role) {
		this.Role = Role;
	}
	
    
    
}
