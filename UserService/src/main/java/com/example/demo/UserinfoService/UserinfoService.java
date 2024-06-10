package com.example.demo.UserinfoService;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.UserRepo.Userrepo;

import com.example.demo.Userinfo.Userinfo;
import com.example.demo.Users.User;

@Service
public class UserinfoService implements UserDetailsService {
	
	
	

	@Autowired
	private Userrepo userrepository;
	
   //Userrepo userrepository = new Userrepo();
	
	public UserinfoService() {
		
	}
	

	/*
	 * @Autowired public UserinfoService(Userrepo userrepo) {
	 * this.userrepository=userrepo; }
	 */
	
	


	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		System.out.println("attttttttttttttt"+username);
		
		
		User users=userrepository.findByName(username);
		
		
		Userinfo user = new Userinfo(users);
        // User up=service.getUser(username);
         
	    System.out.println("from user info service load"+user.getAuthorities());
   
	return user;
		
	}
	

}
