
  package com.example.demo.WebSecurityConfig; 
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
  
  
  import org.springframework.web.cors.CorsConfiguration;
  import org.springframework.web.cors.CorsConfigurationSource; 
  import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
  
  import com.example.demo.UserinfoService.UserinfoService;
  
  
  
  @EnableWebSecurity
   @Configuration 
  public class SecurityConfig {
  
  
  
  
  // UserDetails user = userinfoservice.loadUserByUsername("rahul");
  
  private UserinfoService userinfoservice;
  
  @Autowired public SecurityConfig(UserinfoService userinfoservice) {
  this.userinfoservice=userinfoservice; }
  
  
  

  
  
  

  
  
  @Bean public PasswordEncoder passwordEncoder() { return new
  BCryptPasswordEncoder();
  
  }
  
 
  
  
  @Bean 
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
  throws Exception {
  
  http.cors().and().csrf().disable().securityContext(sc ->
  sc.requireExplicitSave(true)).authorizeHttpRequests((requests) ->
  
  {
	  
	  System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"+requests.toString());
	  try {
		requests.requestMatchers("**/login/**","**/register/**","**/html/**", "**/js/**",
  "**/css/**", "**/user/**", "**/son/**").permitAll()
  .requestMatchers("/user").permitAll()
  
  .requestMatchers("/UserSongs").permitAll()
  .requestMatchers("/accounts").permitAll()
  .requestMatchers("/userinfo").permitAll()
  .requestMatchers("/Authorized").permitAll()
  .requestMatchers("/acesss").hasRole("NORMAL")
  
  .and().formLogin(formLogin -> formLogin .loginPage("/login")
  .loginProcessingUrl("/register") .defaultSuccessUrl("/Authorized")
  .permitAll() )
  
  .logout().logoutUrl("/logout").invalidateHttpSession(true)
  .deleteCookies("JSESSIONID").logoutSuccessUrl("/login");
  
  }
  
  catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace();
  } }
  
  ).sessionManagement(sessionAuthenticationStrategy ->
  sessionAuthenticationStrategy.sessionCreationPolicy(SessionCreationPolicy.
  ALWAYS)) .authenticationProvider(authenticationProvider());
  //.addFilterAfter(JWTFilter, UsernamePasswordAuthenticationFilter.class);
  
  return http.build(); 
  }
  
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
  UrlBasedCorsConfigurationSource source = new
  UrlBasedCorsConfigurationSource(); CorsConfiguration config = new
  CorsConfiguration(); config.addAllowedOrigin("http://localhost:8081"); 
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
 