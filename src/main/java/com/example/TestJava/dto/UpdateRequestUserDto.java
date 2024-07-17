package com.example.TestJava.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequestUserDto {

    private String name;
    private String email;
    private String password;
    private List<PhoneDto> phones;

}
