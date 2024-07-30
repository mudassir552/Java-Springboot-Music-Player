package com.example.demo.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.net.URI;
import java.util.Base64;
import java.util.Map;

@Controller
@CrossOrigin(origins = "*")
public class SpotifyController {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${CALLBACK}")
    private String redirectUri;

    @Autowired
    private RestTemplate restTemplate;
    
    String accessToken;
    
    @GetMapping("/Spotify")
    public String login() {
        String state = generateRandomString(16);
        String scope = "user-read-private user-read-email";
        String authUrl = "https://accounts.spotify.com/authorize?" +
                "response_type=code&client_id=" + clientId +
                "&scope=" + scope +
                "&redirect_uri=" + redirectUri +
                "&state=" + state;
        return "redirect:" + authUrl;
    }

    @GetMapping("/callback")
    public String callback(@RequestParam String code, @RequestParam String state, Model model) {
       
        URI uri = URI.create("https://accounts.spotify.com/api/token");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedCredentials);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            uri, 
            HttpMethod.POST, 
            entity, 
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        // Ensure that the response body is correctly handled
        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null) {
            String accessToken = (String) responseBody.get("access_token");
            
            System.out.println("TOKEN" + accessToken); // Debug log

            model.addAttribute("accessToken", accessToken);
        } else {
            // Handle the case where responseBody is null
            model.addAttribute("error", "Failed to retrieve access token");
        }

        return "Spotify"; // You can return a view name to display the access token or use it for further requests
    }

    private String generateRandomString(int length) {
        StringBuilder text = new StringBuilder();
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; i++) {
            text.append(possible.charAt((int) Math.floor(Math.random() * possible.length())));
        }
        return text.toString();
    }
    
 
    	
    	
    }

