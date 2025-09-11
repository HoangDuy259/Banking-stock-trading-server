package com.example.demo.repository;

import java.util.Optional;

import com.example.demo.entity.User;
import com.example.demo.repository.interfaces.IBaseRepository;

public interface UserRepository extends IBaseRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
}
