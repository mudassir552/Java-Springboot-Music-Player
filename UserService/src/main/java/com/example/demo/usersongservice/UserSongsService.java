package com.example.demo.usersongservice;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.example.demo.SongsDto.SongsDto;

    @Service
     public class UserSongsService {

		String api = "http://songservice:8082/songs";
		
		  @Cacheable(value = "myCache", key = "#root.targetClass + '.' + #root.methodName")
		  public SongsDto[] getUserSongs() {
			  RestTemplate restTemplate = new RestTemplate();

				// Make a GET request to the API and receive the response as a ResponseEntity
				ResponseEntity<SongsDto[]> responseEntity = restTemplate.getForEntity(api, SongsDto[].class);

				return responseEntity.getBody();

		  }
		  
		  @CacheEvict(value = "myCache", key = "#root.targetClass + '.' + #root.methodName")
		    @PostMapping("/evictCacheForGetUserSongs")
		    public void evictCacheForGetUserSongs() {
		        // This method will evict the cache entry for getUserSongs
		    }
	}

