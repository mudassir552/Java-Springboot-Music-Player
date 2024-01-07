package com.example.demo.JWT;

import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;



    @Service
    public class Jwtservice { 
		  
	    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437899994574775757677674758475857485585885"; 
	    public String generateToken(String userName) { 
	        Map<String, Object> claims = new HashMap<>(); 
	        return createToken(claims, userName); 
	    } 
	  
	    private String createToken(Map<String, Object> claims, String userName) { 
	        return Jwts.builder() 
	                .setClaims(claims) 
	                .setSubject(userName) 
	                .setIssuedAt(new Date(System.currentTimeMillis())) 
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30*30))
	                .signWith(getSignKey(), SignatureAlgorithm.HS512) // <-- This can be helpful to you
	                .compact();
	                              
	    } 
	  
	    private Key getSignKey() { 
	        byte[] keyBytes= Decoders.BASE64.decode(SECRET); 
	         return Keys.hmacShaKeyFor(keyBytes);
	    } 
	  
	    public String extractUsername(String token) { 
	        return extractClaim(token, Claims::getSubject); 
	    } 
	  
	    public Date extractExpiration(String token) { 
	        return (Date) extractClaim(token, Claims::getExpiration); 
	    } 
	  
	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { 
	        final Claims claims = extractAllClaims(token); 
	        return claimsResolver.apply(claims); 
	    } 
	  
	    private Claims extractAllClaims(String token) { 
	        return Jwts 
	                .parserBuilder() 
	                .setSigningKey(getSignKey()) 
	                .build() 
	                .parseClaimsJws(token) 
	                .getBody(); 
	    } 
	  
	    private Boolean isTokenExpired(String token) { 
	        return extractExpiration(token).before(new Date(System.currentTimeMillis())); 
	    } 
	  
	    public Boolean validateToken(String token, UserDetails userDetails) { 
	        final String username = extractUsername(token); 
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); 
	    } 
	    
	    
	  
	} 

