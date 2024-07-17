package com.example.TestJava.util;

import com.example.TestJava.repository.UserRepository;
import com.example.TestJava.utils.FormatValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FormatValidatorTest {

    @InjectMocks
    private FormatValidator formatValidator;

    @Mock
    private UserRepository userRepository;

    private final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"; // Ejemplo de regex para email
    private final String passwordRegex = "^[a-zA-Z0-9]*$"; // Ejemplo de regex para password

    @BeforeEach
    void setUp() {
        // Set the regex patterns
        ReflectionTestUtils.setField(formatValidator, "emailRegex", emailRegex);
        ReflectionTestUtils.setField(formatValidator, "passwordRegex", passwordRegex);
    }

    @Test
    void testValidateEmail_ValidEmail() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        assertDoesNotThrow(() -> formatValidator.validateEmail("test@example.com"));
    }

    @Test
    void testValidateEmail_InvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> formatValidator.validateEmail("invalid-email"));
    }

    @Test
    void testValidateEmail_EmailAlreadyExists() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> formatValidator.validateEmail("test@example.com"));
    }

    @Test
    void testValidatePassword_ValidPassword() {
        assertDoesNotThrow(() -> formatValidator.validatePassword("ValidPassword123"));
    }

    @Test
    void testValidatePassword_InvalidPassword() {
        assertThrows(IllegalArgumentException.class, () -> formatValidator.validatePassword("invalid password!"));
    }
}
