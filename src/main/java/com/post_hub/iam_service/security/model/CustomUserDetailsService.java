package com.post_hub.iam_service.security.model;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
        user.setLastLogin(LocalDateTime.now());
        this.userRepository.save(user);
        return new CustomUserDetails(user);
    }

}
