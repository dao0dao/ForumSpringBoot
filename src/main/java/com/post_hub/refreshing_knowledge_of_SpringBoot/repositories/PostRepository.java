package com.post_hub.refreshing_knowledge_of_SpringBoot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
    boolean existsByTitle (String title);
    Optional<Post> findByIdAndDeletedFalse(Integer id);

    @Modifying
    @Query(value =  "INSERT INTO post_likes (userId, postId) VALUES (:userId, :postId) On CONFLICT DO NOTHING", nativeQuery = true)
    void addLIke(@Param("userId") Integer userId, @Param("postId") Integer postId);

    @Modifying
    @Query(value =  "DELETE FROM post_likes WHERE userId = :userId AND postId = :postId",  nativeQuery = true)
    void deleteLike(@Param("userId") Integer userId, @Param("postId") Integer postId);
}
