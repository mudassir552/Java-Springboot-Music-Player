package com.example.demo.SongResponse;




public class Songs {

	private String Id;
   
 

	private String song;
    
   
    private String artist;
    

    private byte[] image;
    

    private byte[] SongFile;
    
   
    
  
    private Long userid;

	public Songs() {
		
	}
	
	

	public Songs(Long userid,String id, String song, String artist, byte[] image, byte[] songFile, Long userId) {
		super();
		Id = id;
		this.song = song;
		this.artist = artist;
		this.image = image;
		SongFile = songFile;
		userid = this.userid;
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
			return userid;
		}

		public void setUserId(Long userId) {
			userid = userId;
		}

}
