package com.post_hub.iam_service.security.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.post_hub.iam_service.model.entities.User;
import com.post_hub.iam_service.model.exception.NotFoundException;
import com.post_hub.iam_service.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws NotFoundException {
        User user = userRepository.findByEmailAndDeletedFalse(email).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        var roles = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();

        return new CustomUserDetails(user.getEmail(), user.getPassword(), roles);
    }

}
