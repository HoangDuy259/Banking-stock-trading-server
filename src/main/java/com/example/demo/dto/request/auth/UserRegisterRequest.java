package com.example.demo.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisterRequest implements Serializable {
    @NotBlank(message = "Email is REQUIRED")
    @Size(max = 100, message = "Email limit is 100 characters")
    @Email(message = "Email is invalid")
    String email;

    @NotBlank(message = "Username is REQUIRED")
    @Size(min = 10, max = 50, message = "Username must be between 10 and 50 characters")
    String username;

    @NotBlank(message = "Password is REQUIRED")
    @Size(max = 50, message = "Password limit is 50 characters")
    String password;

    @NotBlank(message = "Last name is REQUIRED")
    @Size(max = 50, message = "Last name limit is 50 characters")
    String lastName;

    @NotBlank(message = "First name is REQUIRED")
    @Size(max = 50, message = "First name limit is 50 characters")
    String firstName;
}
