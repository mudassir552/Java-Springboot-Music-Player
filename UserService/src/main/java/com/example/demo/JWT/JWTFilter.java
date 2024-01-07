package com.example.demo.JWT;


import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.UserinfoService.UserinfoService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JWTFilter extends OncePerRequestFilter{

	  
	  private Jwtservice jwtservice;
	  
	 
	 
	    
	    @Autowired
	    private UserinfoService userinfoservice; 
	    
	    public JWTFilter() {
	    	
	    }
	    
	    @Autowired
	    public JWTFilter(Jwtservice jwtService) {
	        this.jwtservice=jwtService;
	    }
		/*
		 * @Autowired public JWTFilter (JwtService jwtservice, UserinfoService
		 * userDetailsService) { this.jwtservice=jwtservice;
		 * this.userDetailsService=userDetailsService; }
		 */
	  
	  
	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 
	        String authHeader = request.getHeader("Authorization"); 
	        String token = null; 
	        String username = null; 
	        if (authHeader != null && authHeader.startsWith("Bearer ")) { 
	            token = authHeader.substring(7); 
	           // System.out.println(jwtservice +"JWTTTTTTTT");
	            
	            username = jwtservice.extractUsername(token); 
	            System.out.println("IN JWWWWWTTTTT"+username);
	        } 
	  
	        if (username != null && SecurityContextHolder.getContext().getAuthentication()== null) { 
	        	
	            UserDetails userDetails = userinfoservice.loadUserByUsername(username); 
	            if (jwtservice.validateToken(token, userDetails)) { 
	                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,  userDetails.getAuthorities()); 
	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
	                SecurityContextHolder.getContext().setAuthentication(authToken); 
	            } 
	        } 
	        
	        filterChain.doFilter(request, response); 
		
	}
	
	 

}
