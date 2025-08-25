package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id" , nullable = false, columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "user_name", nullable = false, columnDefinition = "varchar(50)")
    private String userName;

    @Column(name = "email", nullable = false, columnDefinition = "varchar(100)")
    private String email;

    @Column(name = "phone_Num", nullable = false, columnDefinition = "varchar(20)")
    private String phoneNum;

    @Column(name = "password", nullable = false, columnDefinition = "varchar(255)")
    private String password;
}