package com.example.demo.controller;

import com.example.demo.dto.request.roles.RoleRequest;
import com.example.demo.entity.Role;
import com.example.demo.service.role.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
        @Autowired
        private RoleService roleService;

        // Tạo một role mới
        @PostMapping
        public ResponseEntity<Role> createRole(@RequestBody @Valid RoleRequest role) {
            Role savedRole = roleService.createRole(role);
            return ResponseEntity.ok(savedRole);
        }

        // Lấy tất cả các role
        @GetMapping
        public ResponseEntity<List<Role>> getAllRoles() {
            List<Role> roles = roleService.getAllRoles();
            return ResponseEntity.ok(roles);
        }

        // Lấy role theo ID
        @GetMapping("/{id}")
        public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
            Role role = roleService.getRoleById(id);
            return ResponseEntity.ok(role);
        }

        // Cập nhật role
        @PutMapping("/{id}")
        public ResponseEntity<Role> updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequest role) {
            Role updatedRole = roleService.updateRole(id, role);
            return ResponseEntity.ok(updatedRole);
        }

        // Xóa role
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
            roleService.deleteRole(id);
            return ResponseEntity.noContent().build();
        }
}
