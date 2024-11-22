package com.taskflow.demo.records;

import java.util.List;

public record TicketDetailsDto(
        String id,
        String title,
        String description,
        String status,
        List<Comment> comments,
        UserDto ticketUser,
        ProjectDto project,
        UserStoryDetailsDto userStory

) {
}
