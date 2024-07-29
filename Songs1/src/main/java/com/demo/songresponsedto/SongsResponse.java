package com.demo.songresponsedto;


public class SongsResponse {
    private String userId;
   
    private String song;
    private String artist;
    private byte[] image;
    private byte[] songFile;

    private SongsResponse(Builder builder) {
    	
        this.userId = builder.userId;
        this.song = builder.song;
        this.artist = builder.artist;
        this.image = builder.image;
        this.songFile = builder.songFile;
    }

    public static class Builder {
        private String userId;
       
        private String song;
        private String artist;
        private byte[] image;
        private byte[] songFile;

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }
        
     

        public Builder song(String song) {
            this.song = song;
            return this;
        }

        public Builder artist(String artist) {
            this.artist = artist;
            return this;
        }

        public Builder image(byte[] image) {
            this.image = image;
            return this;
        }

        public Builder songFile(byte[] songFile) {
            this.songFile = songFile;
            return this;
        }

        public SongsResponse build() {
            return new SongsResponse(this);
        }
    }

    // Getters for the fields (optional, depending on your requirements)
    public String getUserId() {
        return userId;
    }
    
 

    public String getSong() {
        return song;
    }

    public String getArtist() {
        return artist;
    }

    public byte[] getImage() {
        return image;
    }

    public byte[] getSongFile() {
        return songFile;
    }
}
