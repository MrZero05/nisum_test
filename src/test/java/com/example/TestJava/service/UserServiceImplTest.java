package com.example.TestJava.service;

import com.example.TestJava.dto.*;
import com.example.TestJava.model.User;
import com.example.TestJava.repository.PhoneRepository;
import com.example.TestJava.repository.UserRepository;
import com.example.TestJava.service.impl.UserServiceImpl;
import com.example.TestJava.utils.FormatValidator;
import com.example.TestJava.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private FormatValidator formatValidator;

    @Mock
    private PhoneRepository phoneRepository;

    private RequestUserDto requestUserDto;
    private UpdateRequestUserDto updateRequestUserDto;
    private User user;
    private ResponseUserDto responseUserDto;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userId = UUID.randomUUID();

        requestUserDto = RequestUserDto.builder()
                .name("Test User")
                .email("test@example.com")
                .password("password")
                .phones(Arrays.asList(new PhoneDto("123456789", "01", "91")))
                .build();

        updateRequestUserDto = UpdateRequestUserDto.builder()
                .name("Updated User")
                .email("updated@example.com")
                .password("newpassword")
                .phones(Arrays.asList(new PhoneDto("987654321", "02", "92")))
                .build();

        user = User.builder()
                .user_name("Test User")
                .user_password("password")
                .email("test@example.com")
                .token("mockToken")
                .creation_date(LocalDateTime.now())
                .modified_date(LocalDateTime.now())
                .last_login(LocalDateTime.now())
                .active(true)
                .phones(Arrays.asList())
                .build();

        responseUserDto = ResponseUserDto.builder()
                .idUser(userId)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .token("mockToken")
                .isActive(true)
                .build();
    }

    @Test
    void testCreateUser() {
        when(userMapper.requestUserDtoToUser(any(RequestUserDto.class))).thenReturn(user);
        when(jwtUtil.generateToken(any(String.class))).thenReturn("mockToken");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.userModelToUseresponseUserDto(any(User.class))).thenReturn(responseUserDto);

        ResponseUserDto result = userService.createUser(requestUserDto);

        assertEquals(responseUserDto, result);
    }

    @Test
    void testCreateUser_InvalidEmail() {
        doThrow(IllegalArgumentException.class).when(formatValidator).validateEmail(any(String.class));

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(requestUserDto));
    }

    @Test
    void testCreateUser_InvalidPassword() {
        doThrow(IllegalArgumentException.class).when(formatValidator).validatePassword(any(String.class));

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(requestUserDto));
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(user));
        when(userMapper.userModelToUseresponseUserDto(any(User.class))).thenReturn(responseUserDto);

        ResponseUserDto result = userService.updateUser(userId, updateRequestUserDto);

        assertEquals(responseUserDto, result);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        when(userRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(userId, updateRequestUserDto));
    }
}
