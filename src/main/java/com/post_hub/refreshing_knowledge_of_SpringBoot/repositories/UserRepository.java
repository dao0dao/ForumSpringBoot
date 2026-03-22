package com.post_hub.refreshing_knowledge_of_SpringBoot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsernameOrEmail(String username, String email);

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndDeletedFalse(String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE (u.username = :username OR u.email = :email) AND u.id <> :id")
    boolean existsByUsernameOrEmailAndIdNot(
            @Param("username") String username,
            @Param("email") String email,
            @Param("id") Integer id // podajesz raz
    );
}
