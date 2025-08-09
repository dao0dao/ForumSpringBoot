package com.post_hub.iam_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.post_hub.iam_service.model.enteties.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
