package com.demo.SongsService;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.demo.songs.Songs;

@Service
public interface SongsService {

	public Songs addSongs(@RequestParam("song")String song,@RequestParam("artist")String artist,@RequestParam("image") MultipartFile image);

	
	Songs addSongs(String song, String artist, MultipartFile image, MultipartFile audio);
	
	
}
