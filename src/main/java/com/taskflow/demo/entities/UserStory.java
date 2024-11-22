package com.taskflow.demo.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "userStories")
public class UserStory {
    @Id
    private String id;
    private String title;
    private String description;
    private ObjectId assignedUserId;
    @Field("projectId")
    private ObjectId projectId;
    private String createdAt;
    private String updatedAt;

    public UserStory(String id, String title, String description, ObjectId projectId, String createdAt, String updatedAt, ObjectId assignedUserId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assignedUserId = assignedUserId;
        this.projectId = projectId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ObjectId getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(ObjectId assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObjectId getProjectId() {
        return projectId;
    }

    public void setProjectId(ObjectId projectId) {
        this.projectId = projectId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}