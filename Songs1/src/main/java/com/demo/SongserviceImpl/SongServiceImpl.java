package com.demo.SongserviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.demo.SongsRepo.SongsRepo;
import com.demo.songs.Songs;

import com.demo.SongsService.SongsService;

@Component
public class SongServiceImpl implements SongsService{
	
	
	
	
	@Autowired
	private  SongsRepo songsRepo;
	
	
	  @Autowired 
	  private MongoOperations mongoOperations;

	
	public Songs addSongs(String song, String artist, MultipartFile image) {
		 Songs s1 = new Songs();
		  Songs k =null;
		  if((!image.isEmpty()) && (!song.isEmpty())) {
			  try {
				    InputStream inputStream = image.getInputStream();
				    
				    byte[] fileBytes = new byte[0];
				    int bytesRead;
				    byte[] buffer = new byte[1024];
				  
				    while ((bytesRead=inputStream.read(buffer)) != -1) {
				       
				    	  byte[] tempArray = new byte[fileBytes.length + bytesRead];
				    	    System.arraycopy(fileBytes, 0, tempArray, 0, fileBytes.length);
				    	    System.arraycopy(buffer, 0, tempArray, fileBytes.length, bytesRead);
				    	    fileBytes = tempArray;
				    }
				    inputStream.close();
				   
				    s1.setArtist(artist);
				    s1.setImage(fileBytes);
				    s1.setSong(song);
				   // Close the InputStream when done
				} catch (IOException e) {
				    e.printStackTrace();
					
				}

			    k= songsRepo.insert(s1);
		  }
		return k;
		
	}
    






public Songs addSongs(Long userid,String song, String artist, MultipartFile image, MultipartFile audio) {
    Songs s1 = new Songs();
    Songs k = null;

    if ((!image.isEmpty()) && (!song.isEmpty())) {
        try {
            // Process image and audio files
        	s1.setUserId(userid);
            s1.setArtist(artist);
            s1.setSong(song);
            s1.setImage(readBytesFromInputStream(image.getInputStream()));
            s1.setSongFile(readBytesFromInputStream(audio.getInputStream()));
            

            // Insert the Songs object into the repository
            k = songsRepo.insert(s1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    return k;
}

private byte[] readBytesFromInputStream(InputStream inputStream) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int bytesRead;

    while ((bytesRead = inputStream.read(buffer)) != -1) {
        byteArrayOutputStream.write(buffer, 0, bytesRead);
    }

    return byteArrayOutputStream.toByteArray();
}








}
