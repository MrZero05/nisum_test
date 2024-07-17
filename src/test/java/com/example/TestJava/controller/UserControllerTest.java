package com.example.TestJava.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import com.example.TestJava.dto.PhoneDto;
import com.example.TestJava.dto.RequestUserDto;
import com.example.TestJava.dto.ResponseUserDto;
import com.example.TestJava.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private RequestUserDto requestUserDto;
    private ResponseUserDto responseUserDto;

    @BeforeEach
    void setUp() {
        requestUserDto = RequestUserDto.builder()
                .name("Test User")
                .email("test@example.com")
                .password("password")
                .phones(Arrays.asList(new PhoneDto("123456789", "01", "91")))
                .build();

        responseUserDto = ResponseUserDto.builder()
                .idUser(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .token("mockToken")
                .isActive(true)
                .build();
    }


    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any(RequestUserDto.class))).thenReturn(responseUserDto);

        mockMvc.perform(post("/nisumtest/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestUserDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseUserDto)));
    }
}
