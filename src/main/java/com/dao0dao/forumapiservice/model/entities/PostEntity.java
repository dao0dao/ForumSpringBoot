package com.dao0dao.forumapiservice.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity {
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

    @Setter
    @Column(nullable = false)
    @Size(max = 255)
    private String title;

    @Setter
    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created = LocalDateTime.now();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = { CascadeType.ALL, CascadeType.MERGE }, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime updated = LocalDateTime.now();

    @Setter
    @Column(nullable = false)
    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToMany(mappedBy = "likes", fetch = FetchType.LAZY)
    private Set<UserEntity> likedBy = new HashSet<>();

    @Formula("(select count(*) from posts_likes pl where pl.post_id = {alias}.id)")
    private Integer likesCount = 0;

    @Builder
    private PostEntity(String title, String content, UserEntity user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

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
