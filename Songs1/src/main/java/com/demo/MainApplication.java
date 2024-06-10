package com.demo;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.demo.SongsRepo.SongsRepo;
import com.demo.controller.SongController;


@SpringBootApplication
@CrossOrigin(origins="*")
@ComponentScan(basePackages={"com.demo.*"})
@EnableMongoRepositories(basePackageClasses={SongsRepo.class,SongController.class})
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
@EnableDiscoveryClient
@EnableCaching 
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	
	}
	
	    
}
