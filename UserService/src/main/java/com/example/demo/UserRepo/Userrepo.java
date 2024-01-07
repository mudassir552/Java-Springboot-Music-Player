package com.example.demo.UserRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Users.User;



@Repository
public interface Userrepo extends JpaRepository<User,Long>{
	
	
  
	//User findByUsername("Abdul");
	
    //@Query("select * from user u where u.name=:name")
	User findByName(String name);
	
    @Query(value="select * from User",nativeQuery=true)
    List<User> findAllWithRole();

}