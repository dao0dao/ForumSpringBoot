package com.dao0dao.forumapiservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dao0dao.forumapiservice.model.entities.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Integer>, JpaSpecificationExecutor<PostEntity> {
    Optional<PostEntity> findByIdAndDeletedFalse(Integer id);

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO posts_likes (user_id, post_id) VALUES (:userId, :postId) On CONFLICT DO NOTHING", nativeQuery = true)
    void addLIke(@Param("userId") Integer userId, @Param("postId") Integer postId);
    
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM posts_likes WHERE user_id = :userId AND post_id = :postId", nativeQuery = true)
    void deleteLike(@Param("userId") Integer userId, @Param("postId") Integer postId);
}
