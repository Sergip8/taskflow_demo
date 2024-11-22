package com.taskflow.demo.services;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

import com.taskflow.demo.records.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskflow.demo.entities.Role;
import com.taskflow.demo.entities.User;
import com.taskflow.demo.repositories.RoleRepository;
import com.taskflow.demo.repositories.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequestRecord signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new RuntimeException("Email already taken");
        }

        User user = new User();
        user.setUsername(signUpRequest.username());
        user.setEmail(signUpRequest.email());
        user.setCompanyId(signUpRequest.companyId());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setRoles(Set.of(roleRepository.findByName("ROLE_USER").orElseThrow()));

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public CreateResponse signIn(SignInRequestRecord signInRequest) {
        User user = userRepository.findByEmail(signInRequest.email())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = createToken(user);
        Instant expiration = Instant.now().plus(Duration.ofHours(1));
        var userDto = new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getName).toList(),
                user.getCompanyId()
        );
        var userRes = new SigninResponse(userDto, token);
        return new CreateResponse(userRes, "Success", "usuario autenticado");
    }

    private String createToken(User user) {
        try {
            JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                    .issuer("")
                    .subject(user.getId().toString())
                    .claim("roles", user.getRoles().stream().map(Role::getName).toList())
                    .claim("email", user.getEmail())
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plus(Duration.ofHours(1)))
                    .build();

            return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create JWT token", e);
        }
    }
}
