package com.example.TestJava.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDto {

    private String number;
    private String citycode;
    private String countrycode;

}
