package com.post_hub.iam_service.security.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.post_hub.iam_service.model.entities.User;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {
    private Integer userId;
    private String username;
    private String userEmail;
    private String password;
    private List<SimpleGrantedAuthority> authorities;

    public CustomUserDetails(String userEmail, List<String> roles) {
        // this.userId = user.getId();
        // this.username = user.getUsername();
        this.userEmail = userEmail;
        // this.password = user.getPassword();
        this.authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role)).toList();
    }

    public CustomUserDetails(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.userEmail = user.getEmail();
        this.password = user.getPassword();
        this.authorities = user.getRoles().stream().filter(role -> role.getActive())
                .map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

}
