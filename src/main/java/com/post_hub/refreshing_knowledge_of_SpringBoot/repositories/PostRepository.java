package com.post_hub.refreshing_knowledge_of_SpringBoot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
    boolean existsByTitle(String title);

    Optional<Post> findByIdAndDeletedFalse(Integer id);

    @Modifying
    @Query(value = "INSERT INTO posts_likes (user_id, post_id) VALUES (:userId, :postId) On CONFLICT DO NOTHING", nativeQuery = true)
    @Transactional
    void addLIke(@Param("userId") Integer userId, @Param("postId") Integer postId);
    
    @Modifying
    @Query(value = "DELETE FROM posts_likes WHERE user_id = :userId AND post_id = :postId", nativeQuery = true)
    @Transactional
    void deleteLike(@Param("userId") Integer userId, @Param("postId") Integer postId);
}
