package com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Formula;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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
    @Size(max = 255)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false, updatable = false)
    @Builder.Default()
    private LocalDateTime created = LocalDateTime.now();

    @Column(nullable = false)
    @Builder.Default()
    private LocalDateTime updated = LocalDateTime.now();

    @Column(nullable = false)
    @Builder.Default()
    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "likes", fetch = FetchType.LAZY)
    @Builder.Default()
    private Set<User> likedBy = new HashSet<>();

    @Formula("(select count(*) from posts_likes pl where pl.post_id = id)")
    @Builder.Default()
    private Integer likesCount = 0;

    @PrePersist
    private void initTime() {
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    @PreUpdate
    private void setTime() {
        this.updated = LocalDateTime.now();

    }
}
