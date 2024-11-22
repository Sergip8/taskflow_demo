package com.taskflow.demo.records;

public record CreateUserStoryRequest(
        String title,
        String description,
        String projectId,
        String assignedUserId,
        String ticketTitle,
        String ticketDescription
) {
}
