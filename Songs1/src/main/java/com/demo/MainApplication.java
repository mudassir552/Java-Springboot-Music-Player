package com.demo;
import java.io.*;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.demo.SongsRepo.SongsRepo;
import com.demo.controller.SongController;
import com.demo.songs.Songs;

import jakarta.annotation.PostConstruct;


@SpringBootApplication
@CrossOrigin(origins="*")
@ComponentScan(basePackages={"com.demo.*"})
@EnableMongoRepositories(basePackageClasses={SongsRepo.class,SongController.class})
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
//@EnableDiscoveryClient
@EnableCaching 
public class MainApplication {
	
	@Autowired
	private  SongsRepo songsRepo;

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	
	}
	
	
 @PostConstruct
  public void saveSong() throws IOException {
		
		
	    Songs s = new Songs();
	    
	    try (FileInputStream f = new FileInputStream("src/main/resources/images/zaynp.jpg");
	             FileInputStream k = new FileInputStream("src/main/resources/songs/download.mp3")) {
	            s.setSong("love like this");
	            s.setArtist("zayn");
	            s.setId("1");
	            s.setImage(f.readAllBytes());
	            s.setSongFile(k.readAllBytes());
	            s.setUserId(1L);
	          
	            songsRepo.save(s);
	        } catch (IOException e) {
	            e.printStackTrace();
	            // Handle the exception as needed
	        }
	    }
	    
}
