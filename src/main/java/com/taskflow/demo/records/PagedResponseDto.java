package com.taskflow.demo.records;

public record PagedResponseDto(
        Object data,
        long count,
        int page
) {
}
