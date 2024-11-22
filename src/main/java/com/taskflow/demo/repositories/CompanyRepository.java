package com.taskflow.demo.repositories;

import com.taskflow.demo.entities.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {

}