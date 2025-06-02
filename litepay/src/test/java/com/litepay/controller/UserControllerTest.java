package com.litepay.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litepay.model.User;
import com.litepay.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;
    private UserService userService;
    private ObjectMapper objectMapper;
    private User testUser;

    @BeforeEach
    void setup() {
        userService = Mockito.mock(UserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
        objectMapper = new ObjectMapper();

        testUser = new User("merchant1", "password123", "merchant1@example.com");
        testUser.setId(UUID.randomUUID());
    }

    @Test
    void testRegisterUser() throws Exception {
        when(userService.registerUser(Mockito.any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUser.getId().toString()))
                .andExpect(jsonPath("$.username").value("merchant1"))
                .andExpect(jsonPath("$.email").value("merchant1@example.com"));
    }

    @Test
    void testGetUserById() throws Exception {
        UUID id = testUser.getId();
        when(userService.getUserById(id)).thenReturn(testUser);

        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.username").value("merchant1"))
                .andExpect(jsonPath("$.email").value("merchant1@example.com"));
    }
}
