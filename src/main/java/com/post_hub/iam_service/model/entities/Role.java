package com.post_hub.iam_service.model.entities;

import java.util.Set;

import com.post_hub.iam_service.service.model.UserRole;
import com.post_hub.iam_service.utils.UserRoleTypeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column(name = "user_system_name")
    @Convert(converter = UserRoleTypeConverter.class)
    private UserRole userSystemName;

    @Column
    private Boolean active;

    @Column(name = "created_by")
    private String createdBy;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users;
}
