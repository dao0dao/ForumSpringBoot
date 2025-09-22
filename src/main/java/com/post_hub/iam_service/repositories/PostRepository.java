package com.post_hub.iam_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.post_hub.iam_service.model.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
    boolean existsByTitle (String title);
    Optional<Post> findByIdAndDeletedFalse(Integer id);
}
