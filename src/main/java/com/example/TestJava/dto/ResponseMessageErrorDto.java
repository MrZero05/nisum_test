package com.example.TestJava.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageErrorDto {

    String message;

}
