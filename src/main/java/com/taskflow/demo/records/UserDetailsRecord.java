package com.taskflow.demo.records;

import java.util.Set;

public record UserDetailsRecord(String id, String username, Set<String> roles) {
}
