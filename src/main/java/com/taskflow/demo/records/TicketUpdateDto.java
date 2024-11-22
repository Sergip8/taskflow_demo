package com.taskflow.demo.records;

import org.bson.types.ObjectId;

public record TicketUpdateDto(
        String id,
        String title,
        String description,
        String status

) {
}
