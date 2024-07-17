package com.example.TestJava.controller;

import com.example.TestJava.dto.RequestUserDto;
import com.example.TestJava.dto.ResponseUserDto;
import com.example.TestJava.dto.UpdateRequestUserDto;
import com.example.TestJava.model.User;
import com.example.TestJava.service.IUserService;
import com.example.TestJava.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/nisumtest/")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<ResponseUserDto> createUser(@RequestBody RequestUserDto requestUserDto) {
        ResponseUserDto responseUserDto = userService.createUser(requestUserDto);

        return ResponseEntity.ok(responseUserDto);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<?> updateUser(@PathVariable("uuid") UUID id, @RequestBody UpdateRequestUserDto requestUserDto) {
        try {
            ResponseUserDto updatedUser = userService.updateUser(id, requestUserDto);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
