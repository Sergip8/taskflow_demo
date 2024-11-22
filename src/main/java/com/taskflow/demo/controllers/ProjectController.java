package com.taskflow.demo.controllers;

import com.taskflow.demo.entities.Project;
import com.taskflow.demo.records.ProjectDto;
import com.taskflow.demo.services.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/company/{companyId}")
    public List<ProjectDto> getProjectsByCompany(@PathVariable String companyId) {
        return projectService.getProjectsByCompanyId(companyId);
    }
}