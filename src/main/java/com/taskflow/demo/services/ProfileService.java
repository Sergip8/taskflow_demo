package com.taskflow.demo.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskflow.demo.entities.Role;
import com.taskflow.demo.entities.User;
import com.taskflow.demo.records.UserDetailsRecord;
import com.taskflow.demo.repositories.UserRepository;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDetailsRecord getAuthenticatedUserDetails(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getSubject();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDetailsRecord(
                user.getId(),
                user.getUsername(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
    }
}