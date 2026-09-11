package com.dao0dao.forumapiservice.security.model;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.dao0dao.forumapiservice.model.entities.UserEntity;
import com.dao0dao.forumapiservice.model.exception.NotFoundException;
import com.dao0dao.forumapiservice.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws NotFoundException {
        UserEntity user = userRepository.findByEmailAndDeletedFalse(email).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        user.setLastLogin(LocalDateTime.now());
        this.userRepository.save(user);
        return new CustomUserDetails(user);
    }

}
