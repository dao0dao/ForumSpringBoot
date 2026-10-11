package com.dao0dao.forumapiservice.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dao0dao.forumapiservice.model.entities.PostEntity;
import com.dao0dao.forumapiservice.model.entities.PostEntity_;

public interface PostRepository extends JpaRepository<PostEntity, Integer>, JpaSpecificationExecutor<PostEntity> {
    @Override
    @EntityGraph(attributePaths = PostEntity_.AUTHOR)
    Page<PostEntity> findAll(Specification<PostEntity> spec, Pageable pageable);

    Optional<PostEntity> findByIdAndDeletedFalse(Integer id);

    default Optional<PostEntity> findVisibleById(Integer id, boolean includeDeleted) {
        return includeDeleted ? findById(id) : findByIdAndDeletedFalse(id);
    }

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO posts_likes (user_id, post_id) VALUES (:userId, :postId) On CONFLICT DO NOTHING", nativeQuery = true)
    void addLIke(@Param("userId") Integer userId, @Param("postId") Integer postId);
    
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM posts_likes WHERE user_id = :userId AND post_id = :postId", nativeQuery = true)
    void deleteLike(@Param("userId") Integer userId, @Param("postId") Integer postId);
}
