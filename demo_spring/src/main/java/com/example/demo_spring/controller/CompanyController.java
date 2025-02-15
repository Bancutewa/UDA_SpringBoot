package com.example.demo_spring.controller;

import com.example.demo_spring.model.Company;
import com.example.demo_spring.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/addCompany")
    public String showAddCompanyForm() {
        return "addcompany"; // Trả về trang addcompany.html
    }

    @PostMapping("/saveCompany")
    public String saveCompany(@RequestParam String companyName) {
        Company company = new Company();
        company.setCompanyName(companyName);
        companyService.saveCompany(company);
        return "redirect:/companies"; // Chuyển hướng đến danh sách công ty sau khi lưu
    }

    @GetMapping("/companies")
    public String listCompanies(Model model) {
        List<Company> companies = companyService.getAllCompanies();
        model.addAttribute("companies", companies);
        return "companylist"; // Trả về trang danh sách công ty
    }
}
