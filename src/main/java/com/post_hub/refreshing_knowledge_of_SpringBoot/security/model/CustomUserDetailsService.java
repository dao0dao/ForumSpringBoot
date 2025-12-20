package com.post_hub.refreshing_knowledge_of_SpringBoot.security.model;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.User;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.NotFoundException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.repositories.UserRepository;

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
