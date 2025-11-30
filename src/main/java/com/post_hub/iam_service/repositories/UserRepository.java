package com.post_hub.iam_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.post_hub.iam_service.model.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsernameOrEmail(String username, String email);

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndDeletedFalse(String email);
}
