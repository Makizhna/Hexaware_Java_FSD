package com.example.carrercrafter.controller;

import com.example.carrercrafter.Service.UserService;
import com.example.carrercrafter.controller.api.UserController;
import com.example.carrercrafter.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateUser() throws Exception {
        UserDto userDto = new UserDto(1, "Maki", "maki@example.com", "pass123", null);
        when(userService.saveUser(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("maki@example.com"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<UserDto> users = Arrays.asList(
                new UserDto(1, "User1", "user1@example.com", "pass", null),
                new UserDto(2, "User2", "user2@example.com", "pass", null)
        );

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetUserById() throws Exception {
        UserDto user = new UserDto(1, "John", "john@example.com", "pass123", null);
        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserDto updated = new UserDto(1, "Updated", "updated@example.com", "newpass", null);
        when(userService.updateUser(eq(1), any(UserDto.class))).thenReturn(updated);

        mockMvc.perform(put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    public void testDeleteUserSuccess() throws Exception {
        when(userService.deleteUserAndRelated(1)).thenReturn(true);

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUserNotFound() throws Exception {
        when(userService.deleteUserAndRelated(999)).thenReturn(false);

        mockMvc.perform(delete("/api/user/999"))
                .andExpect(status().isNotFound());
    }
}
