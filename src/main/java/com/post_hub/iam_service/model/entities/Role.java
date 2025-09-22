package com.post_hub.iam_service.model.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column(name = "user_system_name")
    private String userSystemName;

    @Column
    private Boolean active;

    @Column(name = "created_by")
    private String createdBy;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
