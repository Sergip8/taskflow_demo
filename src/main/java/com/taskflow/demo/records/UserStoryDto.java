package com.taskflow.demo.records;

public record UserStoryDto(
        String id,
        String title,
        String description,
        String projectId,
        String assignedUserId,
        String assignedUserName,
        String assignedUserEmail
        //String assignedUserEmail
) {
}
