package com.taskflow.demo.entities;

import com.taskflow.demo.records.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Document(collection = "tickets")
public class Ticket {
    @Id
    private String id;
    private String title;
    private String description;
    private ObjectId assignedUserId;
    @Field("storyId")  // Relación con UserStory
    private ObjectId storyId;

    private String status; // Activo | En Proceso | Finalizado

    private List<Comment> comments;  // Lista de comentarios

    private Instant createdAt;
    private String updatedAt;

    public Ticket(String id, String title, String description, ObjectId storyId, String status, List<Comment> comments, Instant createdAt, String updatedAt, ObjectId assignedUserId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.storyId = storyId;
        this.status = status;
        this.comments = comments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.assignedUserId = assignedUserId;
    }
}
