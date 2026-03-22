package com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.user;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    private String username;

    @Email(message = "email should be valid")
    private String email;

    private String password;

    private String confirmPassword;
}
