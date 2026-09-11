package com.dao0dao.forumapiservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dao0dao.forumapiservice.model.entities.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    
}
