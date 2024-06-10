package com.example.demo.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Exceptions.NoFilesFoundException;
import com.example.demo.FileReader.FileCompressAndDecompress;
import com.example.demo.FileReader.FileReader;
//import  com.example.demo.JWT.Jwtservice;
import com.example.demo.RoleRepo.Rolerepo;
import com.example.demo.Roles.Role;
import com.example.demo.SongsDto.SongsDto;
import com.example.demo.UserRepo.Userrepo;
import com.example.demo.Users.User;
import com.example.demo.usersongservice.UserSongsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private Userrepo userrepo;
	@Autowired
	private Rolerepo rolerepo;

	@Autowired
	private FileReader filereader;

	@Autowired
	private FileCompressAndDecompress FileCompressAndDecompress;

	private static final String SPRING_KEY = "";
	private static final String UPLOAD_DIRECTORY = "src/main/resources/static/images";
	/*
	 * @Autowired private Jwtservice jwtservice;
	 */

	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private UserSongsService userSongService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	
	// String api="http://songservice:8082/songs";

	@Autowired
	private com.example.demo.ConfigDetails.ConfigPropertiesDetails ConfigPropertiesDetails;

	@GetMapping("/UserSongs")
	public String getSongs(Model model, RedirectAttributes redirectAttributes,
			@ModelAttribute("userName") String userName, @ModelAttribute() Authrequest authRequest)
					throws JsonProcessingException {

		

		SongsDto[] songs =userSongService.getUserSongs();
		
		String key = UserSongsService.class.getName() + ".getUserSongs";
        Cache cache = cacheManager.getCache("myCache");

        
      
        if(cache!=null) {
        	
        	logger.info(cache+"cacahe is not null null");
        	Cache.ValueWrapper valueWrapper = cache.get(key);
        	if (valueWrapper != null) {
        		logger.info(valueWrapper+"ValueWrapper is not null");
                SongsDto[] cachedSongs = (SongsDto[]) valueWrapper.get();
                ObjectMapper objectMapper = new ObjectMapper();
        		String jsonString = objectMapper.writeValueAsString(cachedSongs);
        		model.addAttribute("Songs", jsonString);
        		 
        		
            
                
                return "Authorized";
        }
        	
        	cache.put(key, songs);
        	    //logger.info("Cache miss for key: " + key);
        	    
        	  
        	    // Process songs...
        	
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(songs);

		model.addAttribute("Songs", jsonString);
		for (SongsDto s : songs) {
			System.out.println(s.toString());
		}

		redirectAttributes.addFlashAttribute("userName");

		
	}
        return "Authorized";
	}
	
	

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	SecurityContextRepository securityrepo;

	@PostMapping("/user")
	public ResponseEntity<String> addUser(@RequestBody Authrequest user) throws Exception {

		User u = new User();
		User users = userrepo.findByName(user.getUsername());
		ResponseEntity<String> response = null;
		for (Role r : user.getRoles()) {
			
		}
		List<String> roleStrings = user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList());


		try {

			if (roleStrings.size() <= 0 || roleStrings == null) {
				response = ResponseEntity.status(400).body("Enter user roles");
				throw new IllegalArgumentException("please assign roles to user");
			}

			if (user.getEmail().isEmpty()) {
				response = ResponseEntity.status(400).body("Enter Email for user");
				throw new IllegalArgumentException("please assign roles to user");
			}

			if (user.getPassword().isEmpty()) {
				response = ResponseEntity.status(400).body("Enter password for user");
				throw new IllegalArgumentException("please assign roles to user");
			}

			if (users != null && users.getName().length() > 0) {

				return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");

			}

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String password = passwordEncoder.encode(user.getPassword());
			u.setName(user.getUsername());
			u.setPassword(password);
			u.setEmail(user.getEmail());

			List<Role> roles = new ArrayList<>();

			for (Role role : user.getRoles()) {

				Role r = new Role();
				r.setRole(role.getRole());
				roles.add(r);
				u.setRole(roles);
				userrepo.save(u);
				response = ResponseEntity.status(HttpStatus.OK).body("User saved");

			}
		} catch (Exception e) {
			e.printStackTrace();

			response = ResponseEntity.status(500).body("Unexpected error");
		}
		return response;

	}

	@GetMapping("/{id}/getuser")
	public ResponseEntity<?> getUser(@PathVariable long id) {
		try {
			Optional<User> user = userrepo.findById(id);
			if (user.isPresent()) {
				return ResponseEntity.ok(user.get());
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found with given Id");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred while fetching user");
		}
	}

	@PostMapping("/addrole")
	public String addRole(@RequestBody Role role) {
		rolerepo.save(role);
		return "Role saved";

	}

	@PostMapping("/register")
	public String authenticateAndGetToken(Model model, RedirectAttributes redirectAttributes,
			@ModelAttribute("authRequest") @Valid Authrequest authRequest, BindingResult bindingResult,
			HttpServletResponse response, HttpServletRequest request) {
		logger.info("entererd REGISSSTER");
		if (authRequest == null || authRequest.getUsername() == null || authRequest.getPassword() == null) {
			logger.info("entererd ");
			return "null";

		}
		if (bindingResult.hasErrors()) {
			// Handle validation errors
			model.addAttribute("errors", bindingResult.getAllErrors());
			return "error"; // Replace with your actual error view
		}

		try {

			logger.info("entererd tryyyyyy");

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

			if (authentication.isAuthenticated()) {

				SecurityContext context = SecurityContextHolder.getContext();
				context.setAuthentication(authentication);

				securityrepo.saveContext(context, request, response);
				// context.saveContext(context,request,response);
				// String token=jwtservice.generateToken(authRequest.getUsername());
				// System.out.println("JWT TOKENNNN"+token);
				// Cookie c = new Cookie("JSESSIONID",request.getRequestedSessionId());
				// c.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
				// c.setSecure(true);
				// response.addCookie(c);
				// HttpSession session = request.getSession(true);
				// .session.setAttribute(SPRING_KEY, context);

				redirectAttributes.addFlashAttribute("userName");

				return "redirect:/UserSongs";
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			return "/error";

		}
		return "error";
	}

	@GetMapping("/login")
	public String thymleaf(Model model) {
		model.addAttribute("Authrequest", new Authrequest());
		return "login";
	}

	@GetMapping("/Authorized")
	public String user(Model model, @ModelAttribute("userName") String userName) {

		model.addAttribute("userName", userName);

		return "Authorized";

	}

	@GetMapping("/error")
	public String error() {

		return "error";

	}

	@GetMapping("/userinfo")
	public String Userinfo() {
		return "/userinfo";
	}

	@PostMapping("/UserImage")
	public ResponseEntity<?> SaveUserImage(HttpServletRequest req, HttpSession http,
			@RequestParam("image") MultipartFile image) throws IOException {
		if (image.isEmpty() || image == null) {
			throw new NoFilesFoundException("No file is present or file is null");

		}

		else if (securityrepo.containsContext(req)) {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String name = authentication.getName();

			logger.info("Authentication successful for user: " + name);

			// Retrieve user information based on authentication
			User user = userrepo.findByName(name);

			if (user != null) {

				logger.info("IMAGE" + image.getBytes().length);

				byte[] profileImage = filereader.MultiparttoByte(image);
				logger.info("PROFILE LENGTH" + profileImage.length);
				user.setProfileimage(profileImage);
				userrepo.save(user);

				String base64Image = java.util.Base64.getEncoder().encodeToString(profileImage);

				// Return the updated profile image as base64 string
				return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(base64Image);
			}
		}

		// If user is not found or authentication fails, return an error response
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authentication Failed");
	}

	@GetMapping("/ProfileImage")
	public ResponseEntity<?> getprofileImage() throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String Username = authentication.getName();
		byte profileImage[] = null;
		byte decompressedProfileImage[] = null;
		if (Username != null) {
			logger.info("inside profile image" + Username);
			profileImage = userrepo.findByName(Username).getProfileimage();
			decompressedProfileImage = FileCompressAndDecompress.decompressFile(profileImage);

		}
		String base64Image = java.util.Base64.getEncoder().encodeToString(decompressedProfileImage);
		return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(base64Image);

	}

}
