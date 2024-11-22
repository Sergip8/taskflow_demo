package com.taskflow.demo.services;

import com.taskflow.demo.entities.Company;
import com.taskflow.demo.records.CompanyDto;
import com.taskflow.demo.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<CompanyDto> getAllCompanies() {

        var companies = companyRepository.findAll();

        return companies.stream().map(c ->
            new CompanyDto(c.getId(), c.getName(), c.getNit())
        ).toList();
    }
}