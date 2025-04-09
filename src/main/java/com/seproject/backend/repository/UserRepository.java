package com.seproject.backend.repository;

import com.seproject.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM users u WHERE u.username = ?1 OR u.email = ?1 LIMIT 1", nativeQuery = true)
    Optional<User> findByUsernameOrEmail(String username);

    Optional<User> findByEmail(String email);
}