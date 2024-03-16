package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.demo.ConfigDetails.ConfigPropertiesDetails;

import jakarta.persistence.Cacheable;

@SpringBootApplication()
@EnableDiscoveryClient
@Configuration
@EnableCaching
@EnableConfigurationProperties(ConfigPropertiesDetails.class)
//@EnableJpaRepositories("com.example.demo.UserRepo")
//@ComponentScan(basePackages={"com.example.demo.Controllers","com.example.demo.WebSecurityConfig","com.example.demo.UserRepo.Userrepo","com.example.demo.Userinfo","com.example.demo.UserinfoService","com.example.demo.User.User","com.example.demo.Roles.Role","com.example.demo.RoleRepo","com.example.demo.JWT","com.example.demo.JWT.Jwtservice","com.example.demo.UserService"})
@ComponentScan(basePackages={"com.example.demo"})
public class UserServiceApplication {

	public static void main(String[] args) { 
		SpringApplication.run(UserServiceApplication.class, args);
	}
	    @Bean
	    public ConcurrentMapCacheManager cacheManager() {
	        return new ConcurrentMapCacheManager("myCache");
	 }
}
