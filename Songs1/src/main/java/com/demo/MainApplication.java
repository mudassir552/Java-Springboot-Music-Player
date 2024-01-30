package com.demo;



import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.demo.Controller.SongController;
import com.demo.SongsRepo.SongsRepo;


@SpringBootApplication
@ComponentScan(basePackages={"com.demo.*"})
@EnableMongoRepositories(basePackageClasses={SongsRepo.class,SongController.class})
@EnableDiscoveryClient
@EnableCaching 
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	
	}
	
	   @Bean
	    public ConcurrentMapCacheManager cacheManager() {
	        return new ConcurrentMapCacheManager("myCache");
	    }
}
