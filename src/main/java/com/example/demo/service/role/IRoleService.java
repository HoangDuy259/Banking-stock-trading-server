package com.example.demo.service.role;

import com.example.demo.dto.request.roles.RoleRequest;
import com.example.demo.entity.Role;

import java.util.List;

public interface IRoleService {
    Role createRole(RoleRequest role);
    List<Role> getAllRoles();
    Role getRoleById(Long id);
    Role updateRole(Long id, RoleRequest role);
    void deleteRole(Long id);
}
