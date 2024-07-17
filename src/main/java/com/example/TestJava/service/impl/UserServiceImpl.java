package com.example.TestJava.service.impl;

import com.example.TestJava.dto.RequestUserDto;
import com.example.TestJava.dto.ResponseUserDto;
import com.example.TestJava.dto.UpdateRequestUserDto;
import com.example.TestJava.dto.UserMapper;
import com.example.TestJava.model.Phone;
import com.example.TestJava.model.User;
import com.example.TestJava.repository.PhoneRepository;
import com.example.TestJava.repository.UserRepository;
import com.example.TestJava.service.IUserService;
import com.example.TestJava.utils.FormatValidator;
import com.example.TestJava.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final FormatValidator formatValidator;
    private final PhoneRepository phoneRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, JwtUtil jwtUtil, FormatValidator formatValidator, PhoneRepository phoneRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.formatValidator = formatValidator;
        this.phoneRepository = phoneRepository;
    }

    @Override
    @Transactional
    public ResponseUserDto createUser(RequestUserDto requestUserDto) {

        //Format validation for email and password.
        formatValidator.validateEmail(requestUserDto.getEmail());
        formatValidator.validatePassword(requestUserDto.getPassword());

        //userMapper for map RequestUser to User model.
        User user = userMapper.requestUserDtoToUser(requestUserDto);

        user.setLast_login(LocalDateTime.now());
        user.setToken(jwtUtil.generateToken(requestUserDto.getEmail()));

        List<Phone> phones = userMapper.phoneDtosToPhones(requestUserDto.getPhones());

        // add user for each Phone
        User userSaved = userRepository.save(user);
        for (Phone phone : phones) {
            phone.setUser(user);
            phoneRepository.save(phone);
        }


        //userDtoMapper for map model to response
        ResponseUserDto responseUserDto = userMapper.userModelToUseresponseUserDto(userSaved);
        //responseUserDto.setPhones(requestUserDto.getPhones());
        return responseUserDto;
    }

    @Override
    @Transactional
    public ResponseUserDto updateUser(UUID uuid, UpdateRequestUserDto requestUserDto) {
        //Find Existing user
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("User not exist"));

        //Validate and update email
        if (requestUserDto.getEmail() != null) {
            formatValidator.validateEmail(requestUserDto.getEmail());
            user.setEmail(requestUserDto.getEmail());
        }

        //Update password
        if (requestUserDto.getPassword() != null) {
            formatValidator.validatePassword(requestUserDto.getPassword());
            user.setUser_password(requestUserDto.getPassword());
        }

        //Update name
        if (requestUserDto.getName() != null) {
            user.setUser_name(requestUserDto.getName());
        }

        //Update phones
        user.setLast_login(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        if (requestUserDto.getPhones() != null) {

            List<Phone> phones = userMapper.phoneDtosToPhones(requestUserDto.getPhones());
            savedUser = userRepository.save(savedUser);
            for (Phone phone : phones) {
                phone.setUser(savedUser);
                phoneRepository.save(phone);
            }
        }

        return userMapper.userModelToUseresponseUserDto(savedUser);
    }
}
