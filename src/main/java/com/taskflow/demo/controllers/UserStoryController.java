package com.taskflow.demo.controllers;

import com.taskflow.demo.records.*;
import com.taskflow.demo.services.UserStoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userstory")
public class UserStoryController {

    private final UserStoryService userStoryService;

    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }
    @GetMapping("/project/{projectId}")
    public List<UserStoryDto> getProjectsByCompany(@PathVariable String projectId) {
        return userStoryService.findUserStoriesWithUserByProjectId(projectId);
    }
    @PostMapping("/store")
    public CreateResponse storeUserStory(@RequestBody CreateUserStoryRequest request)
    {
        return userStoryService.createUserStoryWithTicket(request);
    }

    @PostMapping("/update")
    public CreateResponse updateUserStory(@RequestBody UpdateUserStoryRequest request)
    {
        return userStoryService.updateUserStory(request);
    }


}
