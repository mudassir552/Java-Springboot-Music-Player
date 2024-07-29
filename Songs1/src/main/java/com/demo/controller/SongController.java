package com.demo.controller;


import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.demo.MainApplication;
import com.demo.SongsRepo.SongsRepo;
import com.demo.SongserviceImpl.*;
import com.demo.songresponsedto.AllSongsResponse;
import com.demo.songresponsedto.SongsResponse;
import com.demo.songs.Songs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.DeleteResult;

import java.io.IOException;
import java.util.*;


@Controller
@CrossOrigin(origins="*")
public class SongController  {
      
	private static final Logger logger = LoggerFactory.getLogger(SongController.class);
	
	  private  RestTemplate restTemplate;
	 
	  
	   @Autowired
	    private MainApplication mainApplication;
	  
	  @Value("${cache.eviction.url}")
	  private String evictionUrl;
	  @Autowired 
	  public SongController (RestTemplate restTemplate) {
	  this.restTemplate = restTemplate; }
	
	    
	    
		 
	 @Autowired
     private SongServiceImpl SongServiceImpl ;
	
    
	@Autowired
	private  SongsRepo songsRepo;
	
	
	  @Autowired 
	  private MongoOperations mongoOperations;
	 
    
    
	  @PostMapping("/AddSong")
	  @ResponseBody
	  public ResponseEntity<?> addSong( @RequestParam("userid") Long userid,
			                 @RequestParam("song") String song,
	                        @RequestParam("artist") String artist,
	                        @RequestParam("image") MultipartFile image,
	                        @RequestParam("songFile") MultipartFile songFile ,
	                        Model model)  {
	      try {
	    	  
	    	  logger.info("Received request to add song:");
	            logger.info("User ID: {}", userid);
	            logger.info("Song: {}", song);
	            logger.info("Artist: {}", artist);
	            logger.info("Image File Name: {}", image.getOriginalFilename());
	            logger.info("Song File Name: {}", songFile.getOriginalFilename());

	          Songs s = SongServiceImpl.addSongs(userid,song, artist, image,songFile);
	          
	       
	          restTemplate.postForObject(evictionUrl, null, Void.class);
	          
	         
	        
	          
	          if (s != null) {
	        	  SongsResponse songResponse = new SongsResponse.Builder()
		        		    .userId(s.getId())
		        		    .song(s.getSong())
		        		    .artist(s.getArtist())
		        		    .image(s.getImage())
		        		    .songFile(s.getSongFile())
		        		    .build();
		          
                return ResponseEntity.status(HttpStatus.CREATED).body(songResponse);
	               
	          } else {
	              
	              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Replace "errorView" with your error view name
	          }
	      } catch (IllegalArgumentException e) {
	    	  logger.error("Illegal Arguments , please check the parameters", e);
	      }
	      
	      catch (Exception e) {
	    	  logger.error("Internal server error", e);
	    	  
	    	  
	    	  return ResponseEntity.status(500).body(null);
	    	  
	      }
		return ResponseEntity.status(500).body(null);
	        
	      }
	  

		
	
	
	@GetMapping("/getsong")
	public String Song(Model model) {
		 model.addAttribute("So", new Songs());
		return "Songs" ;
		
	}
	
	
	
	 
	  
	  @GetMapping("/song") 
public ResponseEntity<List<Songs>> song(@RequestBody Songs s) { 
		  Query query=new Query(Criteria.where("song").is(s.getSong())); //List<Songs> List<Songs>
	  List<Songs> songs=mongoOperations.find(query, Songs.class);
	  
	  
	  if(songs.isEmpty()) {

return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList()); 
}
	  return ResponseEntity.status(200).body(songs);
	  
	  }
	  
	  
	  @GetMapping("/songs")   
        public ResponseEntity<?> songs(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int size) throws IOException { 
		 //List<Songs> List<Songs>
		
		 
		  
		  
			Pageable pageable = PageRequest.of(page, size);
			Query query = new Query()
		            .with(pageable);
			
		logger.info("page"+page);
		logger.info("size"+size);
			
		  List<Songs> songs = mongoOperations.find(query, Songs.class);
		  
		  if(songs.isEmpty()|| songs.size()==0) {
			   
			  mainApplication.saveSong();
		
		  }
		  
		    Long userid=songs.get(0).getUserId();
		  long totalSongs = mongoOperations.count(new Query(), Songs.class);
	        int totalPages = (int) Math.ceil((double) totalSongs / size);
	        logger.info("totalpgaes"+totalPages);
	        HttpHeaders headers = new HttpHeaders();
	        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
	        if (songs.isEmpty()) {
	        	AllSongsResponse emptyResponse = new AllSongsResponse(Collections.emptyList(), totalPages, page,userid);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyResponse);
	            
	        }

	        return ResponseEntity.status(HttpStatus.OK).body(new AllSongsResponse(songs,totalPages,page,userid ));
	  }
	 
	 
		
	@PostMapping("/songslo")  
	public String songs(@ModelAttribute("So")Songs So,Model model,@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
		
	
		List<Songs>song=songsRepo.findBySongStartsWith(So.getSong().toLowerCase());
	
	
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(song);
		//System.out.println( jsonString);
	model.addAttribute("Songs",jsonString);
	return "Songs";
	
	
		
	}
	
	
	    @DeleteMapping("/songs/{Id}")
		public ResponseEntity<?> deleteSong(@PathVariable  String Id) {
			logger.info("Id"+Id);
			  Query query = new Query(Criteria.where("_id").is(Id));
			  
			  DeleteResult result = mongoOperations.remove(query, Songs.class);

			    if (result.getDeletedCount() > 0) {
			    	 restTemplate.postForObject(evictionUrl, null, Void.class);
			        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
			    } else {
			        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
			    }
		}
	}

