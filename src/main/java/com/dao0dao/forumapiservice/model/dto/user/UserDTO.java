package com.dao0dao.forumapiservice.model.dto.user;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.dao0dao.forumapiservice.model.enums.RegistrationStatus;
import com.dao0dao.forumapiservice.model.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    
    private Integer id;
    private String username;
    private Instant created;
    private Instant updated;
    private RegistrationStatus registration_status;
    private List<UserRole> roles;
    private Instant lastLogin;
    private Boolean deleted;
}
