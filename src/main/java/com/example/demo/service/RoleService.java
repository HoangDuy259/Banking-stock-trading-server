package com.example.demo.service;

import com.example.demo.dto.request.roles.RoleRequest;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.interfaces.IRoleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.OffsetDateTime;
import java.util.List;

//@NoArgsConstructor
//@AllArgsConstructor
@Service
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(@Valid @RequestBody RoleRequest role) {
        Role savedRole = new Role();
        savedRole.setRoleName(role.getRoleName());
        savedRole.setDescription(role.getDescription());
        savedRole.setCreatedAt(OffsetDateTime.now());

        return roleRepository.save(savedRole);
    }

    @Override
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    @Override
    public Role updateRole(Long id, RoleRequest role) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        existingRole.setRoleName(role.getRoleName());
        existingRole.setDescription(role.getDescription());
        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        roleRepository.delete(role);
    }
}
