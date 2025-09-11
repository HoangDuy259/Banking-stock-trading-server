package com.example.demo.repository;

import java.util.Optional;

import com.example.demo.entity.User;

public interface UserRepository extends BaseRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
}
