package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.Controllers.UserController;
import com.example.demo.UserRepo.Userrepo;

import java.util.Collections;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="/application-test.properties")
public class UserServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Mock
    private Userrepo userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private UserController registerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterEndpoint() throws Exception {
        String username = "rahman";
        String password = "123";

        User userDetails = new User(username, password, Collections.emptyList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(authenticationManager.authenticate(authentication)).thenReturn(authentication);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .param("username", username)
                .param("password", password))
               .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
               .andExpect(MockMvcResultMatchers.redirectedUrl("/UserSongs"));
    }

    @Test
    public void testRegisterEndpointWithoutParams() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register"))
               .andExpect(MockMvcResultMatchers.status().isOk());
               
    }

    @Test
    public void testRegisterEndpointWithBindingErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .param("username", "") // Assuming username is required
                .param("password", "testpassword"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("error"));
    }
}
