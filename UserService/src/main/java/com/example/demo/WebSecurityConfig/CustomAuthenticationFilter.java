package com.example.demo.WebSecurityConfig;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

	/*
	 * @Override public void doFilter(HttpServletRequest request,
	 * HttpServletResponse response, FilterChain chain) throws IOException,
	 * ServletException { String username = obtainUsername(request); String password
	 * = obtainPassword(request);
	 * logger.info("CustomAuthenticationFilter: doFilter called"); if (username ==
	 * null || password == null) {
	 * 
	 * logger.error("Username or password is null"); // Handle the case where
	 * username or password is null, for example, return an error response // Here
	 * you might throw an exception or set an error response
	 * response.sendError(HttpServletResponse.SC_BAD_REQUEST,
	 * "Username or password cannot be null"); return; }
	 * 
	 * // Delegate the actual authentication to the parent class
	 * super.doFilter(request, response, chain); }
	 */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null || password == null) {
            // Handle the case where username or password is null
            throw new IllegalArgumentException("Username or password cannot be null");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}



