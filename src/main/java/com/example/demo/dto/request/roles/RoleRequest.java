package com.example.demo.dto.request.roles;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class RoleRequest {
    @NotBlank(message = "Role name is required")
    private String roleName;
    private String description;
    private OffsetDateTime createdAt;
}
