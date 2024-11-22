package com.taskflow.demo.records;

public record SigninResponse(
        UserDto user,
        String token
) {
}
