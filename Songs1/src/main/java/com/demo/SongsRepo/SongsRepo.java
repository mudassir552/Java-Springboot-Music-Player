package com.demo.SongsRepo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import com.demo.songs.Songs;
import java.util.*;
import java.util.List;

@Repository
public interface SongsRepo extends MongoRepository<Songs,String>{
	
	//List<Songs>findBySongStartingWith;
	
    
	
	

    @Cacheable("myCache")
	List<Songs> findBySongStartsWith(String song);



	

	

}
