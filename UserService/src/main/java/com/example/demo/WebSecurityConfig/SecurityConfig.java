package com.example.demo.WebSecurityConfig;

import com.example.demo.Controllers.CustomAuthenticationFailureHandler;

import com.example.demo.JWT.*;
import com.example.demo.Userinfo.Userinfo;

import static org.assertj.core.api.Assertions.assertThatIndexOutOfBoundsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import com.example.demo.UserinfoService.UserinfoService;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Slf4j
@Configuration
public class SecurityConfig {

	/*
	 * private UserDetailsService userDetailsService;
	 * 
	 * public SecurityConfig(UserDetailsService userDetailsService) {
	 * this.userDetailsService=userDetailsService; }
	 */
	 
	// UserDetails user = userinfoservice.loadUserByUsername("rahul");

	private UserinfoService userinfoservice;
	  @Autowired 
	  public SecurityConfig(UserinfoService userinfoservice) {
	  this.userinfoservice=userinfoservice; }
	 
	@Autowired
	private Userinfo userinfo;
	
	@Bean
	SecurityContextRepository SecurityContextRepository() {
		return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository()
				  ,new HttpSessionSecurityContextRepository());
	}

	 private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
	@Autowired
	private JWTFilter JWTFilter;
	/*
	 * @Bean public JWTFilter jwtFilter() { return new JWTFilter(); }
	 */

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

	/*
	 * @Bean public UserDetailsService UserDetailsService() {
	 * 
	 * return new UserDetailsService;
	 * 
	 * }
	 */

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.securityContext(sc -> sc.requireExplicitSave(true)).csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((requests) -> {
			try {
				requests
				.requestMatchers("/login","/register", "/js", "/css/**", "/user", "/son").permitAll()
                
				 
				  
				  .requestMatchers("/Authorized").hasRole("NORMAL")
				  .requestMatchers("/acesss").hasRole("NORMAL")
				  
				  .and()
				  .formLogin(formLogin ->
				    formLogin
				        .loginPage("/login")
				        .loginProcessingUrl("/register")
				        .defaultSuccessUrl("/Authorized")
				        .permitAll()
				)
				  
				  .logout().permitAll();

			} 
			
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		).sessionManagement(sessionAuthenticationStrategy ->
        sessionAuthenticationStrategy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterAfter(JWTFilter, UsernamePasswordAuthenticationFilter.class);	

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		// TODO Auto-generated method stub
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userinfoservice);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userinfoservice).passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}
}
