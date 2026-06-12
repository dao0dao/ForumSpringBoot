package com.post_hub.refreshing_knowledge_of_SpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    
}
