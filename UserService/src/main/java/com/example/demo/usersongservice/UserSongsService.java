package com.example.demo.usersongservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Controllers.UserController;
import com.example.demo.SongResponse.Songs;
import com.example.demo.SongsDto.SongsDto;

import java.io.IOException;
import java.util.List;

@Service
public class UserSongsService {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private RestTemplate restTemplate;
	/*
	 * private final String SONG_SERVICE_URL; private final String ANOTHER_URL;
	 * private final RestTemplate restTemplate;
	 * 
	 * public UserSongsService(@Value("${SONG_SERVICE_URL}") String
	 * SONG_SERVICE_URL,
	 * 
	 * @Value("${ANOTHER_URL}") String ANOTHER_URL, RestTemplate restTemplate) {
	 * this.SONG_SERVICE_URL = SONG_SERVICE_URL; this.ANOTHER_URL = ANOTHER_URL;
	 * this.restTemplate = restTemplate; }
	 */

    @Cacheable(value = "myCache", key = "#root.targetClass + '.' + #root.methodName")
    public SongsDto getUserSongs() {
        ResponseEntity<SongsDto> responseEntity = restTemplate.getForEntity("http://localhost:8082/songs", SongsDto.class);
        List<Songs> songsArray = responseEntity.getBody().getSongs();

        int totalPages = responseEntity.getBody().getTotalPages();
        int currentPage = responseEntity.getBody().getCurrentPage();
        Long userid = responseEntity.getBody().getUserId();

        return new SongsDto(songsArray, totalPages, currentPage, userid);
    }

    public ResponseEntity<?> addUserSongs(Long userid,
                                          String song,
                                          String artist,
                                          MultipartFile image,
                                          MultipartFile songFile,
                                          Model model) throws IOException, IllegalArgumentException {
        if (userid != null && song != null && image != null && songFile != null) {
            MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("userid", userid);
            requestBody.add("song", song);
            requestBody.add("artist", artist);
            requestBody.add("image", new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            });
            requestBody.add("songFile", new ByteArrayResource(songFile.getBytes()) {
                @Override
                public String getFilename() {
                    return songFile.getOriginalFilename();
                }
            });

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<?> response = restTemplate.postForEntity("http://localhost:8082/AddSong", requestEntity, String.class);
            return response;
        } else {
            throw new IllegalArgumentException("parameters can not be null");
        }
    }

    
}
