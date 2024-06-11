package com.example.connectify.controllers;

import com.example.connectify.api.ConnectifyApplication;
import com.example.connectify.api.models.UserLoginDTO;
import com.example.connectify.api.models.UserRegistrationDTO;
import com.example.connectify.api.models.UserLoginResponseDTO;
import com.example.connectify.api.services.UserAuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ConnectifyApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthService userAuthService;

    @Test
    public void testLoginUser() throws Exception {
        UserLoginDTO userLoginDTO = new UserLoginDTO("test@example.com", "password123");
        UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO(1L, "testuser", "test@example.com", "token");

        Mockito.when(userAuthService.loginUser(userLoginDTO.getEmail(), userLoginDTO.getPassword()))
                .thenReturn(userLoginResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\", \"password\": \"password123\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.jwt").value("token"));
    }

    @Test
    public void testRegisterUser() throws Exception {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("testuser", "test@example.com", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"email\": \"test@example.com\", \"password\": \"password123\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }
}
