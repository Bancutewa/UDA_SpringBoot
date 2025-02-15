package com.example.demo_spring.service;

import com.example.demo_spring.model.Company;
import com.example.demo_spring.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

    public Optional<Company> getCompanyById(int id) {
        return companyRepository.findById(id);
    }

    public void deleteCompany(int id) {
        companyRepository.deleteById(id);
    }
}
