package com.taskflow.demo.services;

import com.taskflow.demo.entities.Project;
import com.taskflow.demo.records.CompanyDto;
import com.taskflow.demo.records.ProjectDto;
import com.taskflow.demo.repositories.CompanyRepository;
import com.taskflow.demo.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;

    public ProjectService(ProjectRepository projectRepository, CompanyRepository companyRepository) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
    }

    public List<ProjectDto> getProjectsByCompanyId(String companyId) {
        var projects = projectRepository.findByCompanyId(companyId);
        var company = companyRepository.findById(companyId).orElseThrow();

        return projects.stream().map(p ->
                new ProjectDto(p.getId(), p.getName(), p.getDescription(), company.getName())
        ).toList();
    }
}