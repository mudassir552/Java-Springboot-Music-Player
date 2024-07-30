
  package com.example.demo.WebSecurityConfig; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
  import org.springframework.context.annotation.Bean; 
  import org.springframework.context.annotation.Configuration; 
  import org.springframework.security.authentication.AuthenticationManager;
  import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
  
  import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
  
  import org.springframework.security.config.annotation.web.builders.HttpSecurity;
  import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
  
  import org.springframework.security.config.http.SessionCreationPolicy;
  
  import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
  import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
  import org.springframework.web.cors.CorsConfigurationSource; 
  import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.UserinfoService.UserinfoService;

;
  
  @EnableWebSecurity
   @Configuration 
  public class SecurityConfig {
  
  
	  private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
  
  // UserDetails user = userinfoservice.loadUserByUsername("rahul");
  
  private UserinfoService userinfoservice;
  
  @Autowired 
  public SecurityConfig(UserinfoService userinfoservice) {
  this.userinfoservice=userinfoservice; }
  
  
  
@Bean
SecurityContextRepository securityContextRepository() {
	
	return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository()
			,new HttpSessionSecurityContextRepository());
}
  
  
  

  
  
  @Bean 
  public PasswordEncoder passwordEncoder() { return new
  BCryptPasswordEncoder();
  
  }
  
 
  
  
  @Bean 
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
  throws Exception {
  
  http.cors().and().csrf().disable().securityContext(sc ->
  sc.requireExplicitSave(true)).authorizeHttpRequests((requests) ->
  
  {
	  
	  
	  try {
		  
		  logger.info("enter security configggggggggg");
		requests.requestMatchers("**/login/**","**/html/**", "**/js/**",
  "**/css/**", "**/user/**","**/html/**", "**/son/**","/favicon.ico","/webjars/**","/css/styles.css","/error","/","public/","static/","/ProfileImage","resources/","**/Song.js/**","**/signup/**","**/saveusers/**","**/images/**","/UserImage","*/*.jpg").permitAll()
  
  .requestMatchers("/user","/evictCacheForGetUserSongs","/actuator/**","/register","/accounts","/acesss","/Authorized","/userinfo","/config","/**/Spotify/**","/logins","/**/callback/**").permitAll()
  
  .requestMatchers("/AuthDetails").authenticated()
  .requestMatchers("/UserSongs","**/createsongs/**").authenticated()
  .anyRequest().authenticated()
  .and().formLogin(formLogin -> formLogin.loginPage("/login")
		  
  .loginProcessingUrl("/register").defaultSuccessUrl("/UserSongs") .failureUrl("/login?error=true")
  )
  .logout().logoutUrl("/logout").invalidateHttpSession(true)
  .deleteCookies("JSESSIONID").logoutSuccessUrl("/login");
  //.and().authorizeHttpRequests()
  //.requestMatchers("/AuthDetails").authenticated();
  
  }
  
  catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace();
  } 
	  }
  
  ).sessionManagement(sessionAuthenticationStrategy ->
  sessionAuthenticationStrategy.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)).authenticationProvider(authenticationProvider());
  
  //.addFilterBefore(new CustomAuthenticationFilter(authenticationManager(http)), UsernamePasswordAuthenticationFilter.class);

  //.addFilterAfter(JWTFilter, UsernamePasswordAuthenticationFilter.class);
  
  return http.build(); 
  }
  
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
  UrlBasedCorsConfigurationSource source = new
  UrlBasedCorsConfigurationSource(); CorsConfiguration config = new
  CorsConfiguration(); 
  config.addAllowedOrigin("*"); 
  config.addAllowedMethod("*");
  config.addAllowedHeader("*"); config.setAllowCredentials(true);
  source.registerCorsConfiguration("/**", config); return source; }
  
  @Bean 
  public AuthenticationProvider authenticationProvider() { // TODO
  DaoAuthenticationProvider authenticationProvider =
  new DaoAuthenticationProvider();
  authenticationProvider.setUserDetailsService(userinfoservice);
  authenticationProvider.setPasswordEncoder(passwordEncoder()); return
  authenticationProvider; }
  
  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http)
  throws Exception {
  
  AuthenticationManagerBuilder authenticationManagerBuilder = http
  .getSharedObject(AuthenticationManagerBuilder.class);
  authenticationManagerBuilder.userDetailsService(userinfoservice).
  passwordEncoder(passwordEncoder()); return
  authenticationManagerBuilder.build(); } }
 