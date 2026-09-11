package com.dao0dao.forumapiservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dao0dao.forumapiservice.model.entities.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer>{
    Optional<RoleEntity> findByName(String name);
}
