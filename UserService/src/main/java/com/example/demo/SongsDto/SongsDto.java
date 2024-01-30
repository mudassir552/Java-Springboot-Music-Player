package com.example.demo.SongsDto;



public class SongsDto {

	    
		private String Id;
	   
		private String song;
	    
	
	    private String artist;
	    
	    
	    private byte[] image;
	    
	
	    private byte[] SongFile;
	    
	    
	    private Long UserId;

		public SongsDto() {
			
		}
		
		

		public SongsDto(String id, String song, String artist, byte[] image, byte[] songFile, Long userId) {
			super();
			Id = id;
			this.song = song;
			this.artist = artist;
			this.image = image;
			this.SongFile = songFile;
			this.UserId = userId;
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
