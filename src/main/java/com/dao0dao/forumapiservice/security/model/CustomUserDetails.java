package com.dao0dao.forumapiservice.security.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dao0dao.forumapiservice.model.entities.UserEntity;
import com.dao0dao.forumapiservice.model.enums.RegistrationStatus;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {
    private Integer userId;
    private String username;
    private String userEmail;
    private String password;
    private RegistrationStatus registrationStatus;
    private List<SimpleGrantedAuthority> authorities;

    public CustomUserDetails(UserEntity user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.userEmail = user.getEmail();
        this.password = user.getPassword();
        this.authorities = user.getRoles().stream().filter(role -> role.getActive())
                .map(role -> new SimpleGrantedAuthority(role.getName())).toList();
        this.registrationStatus = user.getRegistrationStatus();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isEnabled(){
        return this.registrationStatus == RegistrationStatus.ACTIVE;
    }

}
