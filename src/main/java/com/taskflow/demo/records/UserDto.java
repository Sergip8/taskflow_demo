package com.taskflow.demo.records;

import java.util.List;

public record UserDto(
        String id,
        String username,
        String email,
        List<String> roles,
        String companyId
) {
}
