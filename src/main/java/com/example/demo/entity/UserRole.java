package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_roles")
@Data
@NoArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_roles_id" , nullable = false, updatable = false)
    private Long userRoleId;

    @Column(name = "role_id" , nullable = false, updatable = false)
    private Long roleId;

    @Column(name = "user_id" , nullable = false, columnDefinition = "uuid", updatable = false)
    private UUID userId;

    @Column(name="assigned_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime assignedAt;




}
