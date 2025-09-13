package com.post_hub.iam_service.model.request.user;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest implements Serializable {
    
    @NotBlank(message = "username can not be empty")
    private String username;

    @NotBlank(message = "email can not be empty")
    private String email;

    @NotBlank(message = "password can not be empty")
    private String password;
}
