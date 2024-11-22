package com.taskflow.demo.controllers;

import com.taskflow.demo.entities.Company;
import com.taskflow.demo.records.CompanyDto;
import com.taskflow.demo.services.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/all")
    public List<CompanyDto> getAllCompanies() {
        return companyService.getAllCompanies();
    }
}