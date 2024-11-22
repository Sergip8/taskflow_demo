package com.taskflow.demo.records;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public record TicketDto(
        String id,
        String title,
        String description,
        ObjectId assignedUserId,
        String assignedUserEmail,
        String assignedUsername,
        String storyId,
        String status,
        List<Comment> comments

) {
}
