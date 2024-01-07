package com.example.demo.RoleRepo;

import com.example.demo.Roles.*;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Rolerepo  extends JpaRepository<Role,Long>{
    
	//List<Role>findByRoleName(String Name);
}
