package com.example.TestJava.controller;

import com.example.TestJava.dto.RequestUserDto;
import com.example.TestJava.dto.ResponseUserDto;
import com.example.TestJava.dto.UpdateRequestUserDto;
import com.example.TestJava.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/nisumtest/")
@Api(value = "User Management System", tags = "User")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    @ApiOperation(value = "Create a new user", response = ResponseUserDto.class)
    public ResponseEntity<ResponseUserDto> createUser(
            @ApiParam(value = "User details to be created", required = true)
            @RequestBody RequestUserDto requestUserDto) {
        ResponseUserDto responseUserDto = userService.createUser(requestUserDto);

        return ResponseEntity.ok(responseUserDto);
    }

    @PatchMapping("/{uuid}")
    @ApiOperation(value = "Update an existing user", response = ResponseUserDto.class)
    public ResponseEntity<?> updateUser(
            @ApiParam(value = "UUID of the user to be updated", required = true)
            @PathVariable("uuid") UUID id,
            @ApiParam(value = "User details to be updated", required = true)
            @RequestBody UpdateRequestUserDto requestUserDto) {
        try {
            ResponseUserDto updatedUser = userService.updateUser(id, requestUserDto);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
