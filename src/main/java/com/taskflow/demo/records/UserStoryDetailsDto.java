package com.taskflow.demo.records;

public record UserStoryDetailsDto(
        String id,
        String title,
        String description,
        UserDto assignedUser
) {
}
