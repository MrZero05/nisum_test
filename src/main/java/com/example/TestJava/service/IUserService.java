package com.example.TestJava.service;

import com.example.TestJava.dto.RequestUserDto;
import com.example.TestJava.dto.ResponseUserDto;
import com.example.TestJava.dto.UpdateRequestUserDto;
import com.example.TestJava.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IUserService {

    ResponseUserDto createUser(RequestUserDto requestUserDto);
    ResponseUserDto updateUser(UUID id, UpdateRequestUserDto requestUserDtoUpdate);
}
