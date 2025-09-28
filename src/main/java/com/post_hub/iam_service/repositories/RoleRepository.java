package com.post_hub.iam_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.post_hub.iam_service.model.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    Optional<Role> findByName(String name);
}
