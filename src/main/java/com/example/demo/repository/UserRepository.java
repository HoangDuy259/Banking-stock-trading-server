package com.example.demo.repository;

import java.util.Optional;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByUserId();

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

}
