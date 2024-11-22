package com.taskflow.demo.records;

public record Comment (
        String authorId,
        String message,
        String createdAt

){}