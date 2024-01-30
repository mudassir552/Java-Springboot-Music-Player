package com.demo.songs;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.processing.Generated;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.multipart.MultipartFile;



import org.springframework.data.annotation.Id;


@Document("songs")
@org.springframework.cache.annotation.Cacheable
public class Songs {

    @Id
	private String Id;
   
 

	@Field
	private String song;
    
    @Field
    private String artist;
    
    @Field
    private byte[] image;
    
    @Field
    private byte[] SongFile;
    
    @Field
    private Long UserId;

	public Songs() {
		
	}
	
	

	public Songs(String id, String song, String artist, byte[] image, byte[] songFile, Long userId) {
		super();
		Id = id;
		this.song = song;
		this.artist = artist;
		this.image = image;
		SongFile = songFile;
		UserId = userId;
	}




	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	   public String getArtist() {
			return artist;
		}

		public void setArtist(String artist) {
			this.artist = artist;
		}





		public byte[] getImage() {
			return image;
		}





		public void setImage(byte[] image) {
			this.image = image;
		}



		public byte[] getSongFile() {
			return SongFile;
		}



		public void setSongFile(byte[] songFile) {
			SongFile = songFile;
		}
		
		public Long getUserId() {
			return UserId;
		}

		public void setUserId(Long userId) {
			UserId = userId;
		}

	
}
