package com.taskflow.demo.repositories;

import com.taskflow.demo.entities.UserStory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserStoryRepository extends MongoRepository<UserStory, String> {
    List<UserStory> findByProjectId(String projectId);
}
