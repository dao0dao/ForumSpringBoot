package com.post_hub.iam_service.model.enteties;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Post {
    public static final String ID_FIELD = "id";
    public static final String TITLE_FIELD = "title";
    public static final String CONTENT_FIElD = "content";
    public static final String CREATED_FIELD = "created";
    public static final String UPDATED_FIELD = "updated";
    public static final String LIKES_FIELD = "likes";
    public static final String DELETED_FIELD = "deleted";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Builder.Default()
    private LocalDateTime created = LocalDateTime.now();

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Builder.Default()
    private LocalDateTime updated = LocalDateTime.now();
    
    @Column(nullable = false, columnDefinition = "integer default 0")
    @Builder.Default()
    private Integer likes = 0;
    
    @Column(nullable = false, columnDefinition = "boolean default false")
    @Builder.Default()
    private Boolean deleted = false;
}
