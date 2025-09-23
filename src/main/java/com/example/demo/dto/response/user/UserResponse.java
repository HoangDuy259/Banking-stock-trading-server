package com.example.demo.dto.response.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
     Long id;
     String firstName;
     String lastName;
     String email;
     String username;
}
