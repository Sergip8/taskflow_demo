package com.taskflow.demo.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.taskflow.demo.entities.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String Email);
}