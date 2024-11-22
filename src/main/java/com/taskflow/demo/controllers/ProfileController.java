package com.taskflow.demo.controllers;

import com.taskflow.demo.records.UserDetailsRecord;
import com.taskflow.demo.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ResponseEntity<UserDetailsRecord> getProfile(@PathVariable Authentication authentication) {
        UserDetailsRecord userDetails = profileService.getAuthenticatedUserDetails(authentication);

        return ResponseEntity.ok(userDetails);
    }
}