package com.post_hub.iam_service.model.dto.role;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.post_hub.iam_service.service.model.UserRole;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    @JsonIgnore
    private Integer id;
    private String name;
    private UserRole userSystemName;
    private Boolean active;
    private String createdBy;
}
