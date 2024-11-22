package com.taskflow.demo.records;

public record ProjectDto(
        String id,
        String name,
        String description,
        String companyName
) {
}
