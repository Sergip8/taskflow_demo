package com.taskflow.demo.records;

import java.time.Instant;

public record SignInResponseRecord(SigninResponse data, Instant expiration) {
}


