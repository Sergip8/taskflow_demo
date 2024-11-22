package com.taskflow.demo.repositories;

import com.taskflow.demo.entities.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByCompanyId(String companyId);
}
