package com.post_hub.refreshing_knowledge_of_SpringBoot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    boolean existsByUsernameOrEmail(String username, String email);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmailAndDeletedFalse(String email);

    @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE (u.username = :username OR u.email = :email) AND u.id <> :id")
    boolean existsByUsernameOrEmailAndIdNot(
            @Param("username") String username,
            @Param("email") String email,
            @Param("id") Integer id // podajesz raz
    );
}
