package com.taskflow.demo.records;

public record TicketFilter(
        int page,
        int size,
        String status,
        String order,
        String orderDirection,
        String search,
        String companyId
) {
}
