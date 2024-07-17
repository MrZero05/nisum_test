package com.example.TestJava.utils;

import com.example.TestJava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class FormatValidator {

    @Value("${app.email.regex}")
    private String emailRegex;

    @Value("${app.password.regex}")
    private String passwordRegex;

    private final UserRepository userRepository;

    @Autowired
    public FormatValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // Method to validate email
    public void validateEmail(String email) {
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (email == null || !emailPattern.matcher(email).matches()) {
            throw new IllegalArgumentException("The email is not valid");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Already exist a User with the email: " + email);
        }
    }

    // Method to validate password
    public void validatePassword(String password) {
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        if (password == null || !passwordPattern.matcher(password).matches()) {
            throw new IllegalArgumentException("La contraseña solo puede contener números y letras");
        }
    }
}
