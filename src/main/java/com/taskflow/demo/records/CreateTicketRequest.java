package com.taskflow.demo.records;

import org.bson.types.ObjectId;

import java.util.List;

public record CreateTicketRequest(
        String title,
        String description,
        ObjectId assignedUserId,
        String storyId

) {
}
