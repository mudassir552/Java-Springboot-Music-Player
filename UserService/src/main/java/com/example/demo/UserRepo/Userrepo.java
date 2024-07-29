package com.example.demo.UserRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.example.demo.Roles.Role;
import com.example.demo.Users.User;

import ch.qos.logback.classic.Logger;



@Repository
public interface Userrepo extends JpaRepository<User,Long>,CrudRepository<User,Long>{
	
	
	 PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	
    //@Query("select * from user u where u.name=:name")
	User findByName(String name);
	
	
    @Query(value="select * from User",nativeQuery=true)
    List<User> findAllWithRole();
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO User (username,password,email) VALUES (:username,:password,:email)", nativeQuery = true)
    public void saved(String username,String password,String email);
    	
    	
    @Transactional
    @Modifying
    default String  saveUserWithRole(String username, String password, String email, List<String>roles) {
        //saved(username, password, email); // Insert the user first

    	User user = new User();
    	user.setName(username);
    	user.setPassword(passwordEncoder.encode(password));
    	user.setEmail(email);
    	
         save(user);
   
    
    		
    		
    		
    	
        User user1 = findByName(username); 
    
        if(user1!=null) {
        	 user1.setRole(roles.get(0));
        	 save(user1);
        	return "user saved";
        }
        
        return null;
    }

    
   
}


