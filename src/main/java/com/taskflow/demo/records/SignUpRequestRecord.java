package com.taskflow.demo.records;


public record SignUpRequestRecord(
        String username,
        String email,
        String companyId,
        String password) {
}