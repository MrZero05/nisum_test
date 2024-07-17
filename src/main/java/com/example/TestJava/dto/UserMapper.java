package com.example.TestJava.dto;

import com.example.TestJava.model.Phone;
import com.example.TestJava.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.xml.ws.Response;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "user_name", source = "name")
    @Mapping(target = "user_password", source = "password")
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "creation_date", ignore = true)
    @Mapping(target = "modified_date", ignore = true)
    @Mapping(target = "last_login", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "phones", ignore = true)
    User requestUserDtoToUser(RequestUserDto requestUserDto);

    @Mapping(target = "idUser", source = "uuid")
    @Mapping(target = "createdDate", source = "creation_date")
    @Mapping(target = "modifiedDate", source = "modified_date")
    @Mapping(target = "lastLogin", source = "last_login")
    @Mapping(target = "isActive", source = "active")
    ResponseUserDto userModelToUseresponseUserDto(User user);

    List<Phone> phoneDtosToPhones(List<PhoneDto> phones);
}

