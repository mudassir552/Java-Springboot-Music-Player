package com.example.demo.Controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;


import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import  com.example.demo.JWT.Jwtservice;
import com.example.demo.RoleRepo.Rolerepo;
import com.example.demo.Roles.Role;
import com.example.demo.SongsDto.SongsDto;
import com.example.demo.UserRepo.Userrepo;
import com.example.demo.Users.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Cacheable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@CrossOrigin(origins="http://localhost:8081")
public class UserController {

	
	@Autowired 
	private Userrepo userrepo;
	@Autowired
	private Rolerepo rolerepo;
	
	/*
	 * @Autowired private Jwtservice jwtservice;
	 */
	
	
	
	//@Autowired
	//private SecurityContextRepository securityContext;
	
	
	//String api="http://localhost:8082/songs";
	String api="http://songservice:8082/songs";
	  
	@Autowired
	private com.example.demo.ConfigDetails.ConfigPropertiesDetails ConfigPropertiesDetails;

	
	
	@GetMapping("/UserSongs")
	@org.springframework.cache.annotation.Cacheable(value = "myCache")
	 public String getSongs(Model model,RedirectAttributes redirectAttributes,@ModelAttribute("userName") String userName,@ModelAttribute()Authrequest authRequest) throws JsonProcessingException {
	//System.out.println("hiiiiii"+userName);
	RestTemplate restTemplate = new RestTemplate();

    // Make a GET request to the API and receive the response as a ResponseEntity
    ResponseEntity<SongsDto[]> responseEntity = restTemplate.getForEntity(api, SongsDto[].class);
    
    SongsDto[] songs = responseEntity.getBody();
    
    ObjectMapper objectMapper = new ObjectMapper();
	String jsonString = objectMapper.writeValueAsString(songs);
   
    
    model.addAttribute("Songs",jsonString);
    for(SongsDto s : songs){
    	System.out.println(s.toString());
    }
  

  redirectAttributes.addFlashAttribute("userName");
     
       return "Authorized";
	 }
    
	


	@Autowired
    private  AuthenticationManager authenticationManager;
  
  
   
     @PostMapping("/user")
     public ResponseEntity<String> addUser(@RequestBody Authrequest user) throws Exception {
	  
    	 User u=new User();			 
	   User users=userrepo.findByName(user.getUsername());
	   ResponseEntity<String> response=null;
	   for(Role r :user.getRoles()) {
		   System.out.println("Rolesssssssssssss"+r.getRole());
	   }
	   List<String> roleStrings = user.getRoles().stream().map(r ->r.getRole()) .collect(Collectors.toList());
	   
			    System.out.println("Rolesssssss"+roleStrings);
			    System.out.println("Rolesssssss"+roleStrings.size());
	   
	   try {
		   
		   if(roleStrings.size()<=0 || roleStrings==null) {
			    response= ResponseEntity.status(400).body("Enter user roles");
			   throw new IllegalArgumentException("please assign roles to user");
		   }
		   
		   if(user.getEmail().isEmpty() ) {
			    response= ResponseEntity.status(400).body("Enter Email for user");
			   throw new IllegalArgumentException("please assign roles to user");
		   }
		   
		   if(user.getPassword().isEmpty() ) {
			    response= ResponseEntity.status(400).body("Enter password for user");
			   throw new IllegalArgumentException("please assign roles to user");
		   }
		   
		   
		   
		   if(users!=null && users.getName().length()>0) {
			   
			   return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
			  
			   
		   }
		   
		   
	
			   
		   BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		  String password= passwordEncoder.encode(user.getPassword());
		  u.setName(user.getUsername());
		  u.setPassword(password);
		  u.setEmail(user.getEmail());
		 
		 
		  List<Role> roles = new ArrayList<>();
		 
          for (Role role : user.getRoles()) {
             
                 Role r=new Role();
                 r.setRole(role.getRole());
                 roles.add(r);
                 u.setRole(roles);
      		   userrepo.save(u);
                 response =ResponseEntity.status(HttpStatus.OK).body("User saved");
          
        
		   
		   }
	   }
	   catch(Exception e ) {
		   e.printStackTrace();
		  
		   response = ResponseEntity.status(500).body("Unexpected error");
	   }
	   return response;
	   
   }
	   
     
	  
	  @GetMapping("/{id}/getuser")
	  public ResponseEntity<?> getUser(@PathVariable long id) {
	      try {
	          Optional<User> user = userrepo.findById(id);
	          if (user.isPresent()) {
	              return ResponseEntity.ok(user.get());
	          } else {
	              return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found with given Id");
	          }
	      } catch (Exception e) {
	          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred while fetching user");
	      }
	  }

      @PostMapping("/addrole")
	   public String addRole(@RequestBody Role role) {
    	  rolerepo.save(role);
		return "Role saved";
    	  
      }
      
 @PostMapping("/register" ) 
 public String authenticateAndGetToken(Model model,RedirectAttributes redirectAttributes, @ModelAttribute()Authrequest authRequest,HttpServletResponse response,HttpServletRequest request) { 
	

try {	  
	 
Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

  if(authentication.isAuthenticated()) { 
	  
	SecurityContext context =SecurityContextHolder.getContext();
  context.setAuthentication(authentication);
  //securityContext.saveContext(context,request,response); 
  //String token=jwtservice.generateToken(authRequest.getUsername());
 // System.out.println("JWT TOKENNNN"+token); 
  //Cookie c = new Cookie(token,token);
 // c.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
 // c.setSecure(true); 
 // response.addCookie(c);
 

 
  redirectAttributes.addFlashAttribute("userName");

  return "redirect:/UserSongs";
  }
}

catch(Exception e) {
          e.printStackTrace();
          return "/error";
          
      }
return "/error";
  }
      
		  @GetMapping("/login") 
		 public String thymleaf(Model model) {
		 model.addAttribute("Authrequest", new Authrequest()); 
		 return "login";
		 }
		
			
     @GetMapping("/Authorized")
     public String user(Model model,@ModelAttribute("userName") String userName) {
			  
			  model.addAttribute("userName",userName);
			  
			  //String userNam = (String) model.asMap().get("userName");
			  
			  return "Authorized";
			  
     }
			 
	
  
    
@GetMapping("/error")
public String error() {
	
	 return "error"; 
	 
}


     @GetMapping("/accounts")
     @ResponseBody
     public  String getAccounts() {
    	    String accountList =ConfigPropertiesDetails.message();
    	 return accountList;
    	 
     }
     
     @GetMapping("/userinfo")
     public String Userinfo() {
    	 return "/userinfo";
     }
     

	 }

