package com.post_hub.iam_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.post_hub.iam_service.model.enteties.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsernameOrEmail(String username, String email);
}
