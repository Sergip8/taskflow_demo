package com.taskflow.demo.records;

public record UpdateUserStoryRequest(
        String id,
        String title,
        String description

) {
}
