package com.post_hub.iam_service.model.enteties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.post_hub.iam_service.model.enums.RegistrationStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 30)
    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String password;

    @Size(max = 50)
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false)
    @Builder.Default()
    private LocalDateTime created = LocalDateTime.now();

    @Column(nullable = false)
    @Builder.Default()
    private LocalDateTime updated = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default()
    private RegistrationStatus registration_status = RegistrationStatus.INACTIVE;

    @Column
    private LocalDateTime last_login;

    @Column
    @Builder.Default()
    private Boolean deleted = false;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default()
    private List<Post> posts = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void toLowercaseFields() {
        if (this.username != null) {
            this.username = this.username.toLowerCase();
        }
        if (this.email != null) {
            this.email = this.email.toLowerCase();
        }
    }
}
