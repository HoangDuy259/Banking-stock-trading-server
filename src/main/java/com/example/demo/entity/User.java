package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "firstname", nullable = false, length = 50)
    String firstName;

    @Column(name = "lastname", nullable = false, length = 50)
    String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    String email;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    String username;

    @Column(name = "password", nullable = false, length = 255)
    String password;

    Boolean enabled;
}