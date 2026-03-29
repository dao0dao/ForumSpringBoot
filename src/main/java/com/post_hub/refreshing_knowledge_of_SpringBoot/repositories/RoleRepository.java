package com.post_hub.refreshing_knowledge_of_SpringBoot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer>{
    Optional<RoleEntity> findByName(String name);
}
