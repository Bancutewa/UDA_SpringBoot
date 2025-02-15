package com.example.demo_spring.controller;

import com.example.demo_spring.model.Company;
import com.example.demo_spring.model.User;
import com.example.demo_spring.service.CompanyService;
import com.example.demo_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    // Hiển thị form thêm công ty
    @GetMapping("/addCompany")
    public String showAddCompanyForm(Model model) {
        model.addAttribute("company", new Company());
        return "addcompany"; // Trả về trang addcompany.html
    }

    // Lưu công ty (Thêm mới hoặc cập nhật)
    @PostMapping("/saveCompany")
    public String saveCompany(@ModelAttribute Company company) {
        companyService.saveCompany(company);
        return "redirect:/companies"; // Chuyển hướng đến danh sách công ty sau khi lưu
    }

    // Hiển thị danh sách công ty
    @GetMapping("/companies")
    public String listCompanies(Model model) {
        List<Company> companies = companyService.getAllCompanies();
        model.addAttribute("companies", companies);
        return "companylist"; // Trả về trang danh sách công ty
    }

    // Hiển thị danh sách user của công ty
    @GetMapping("/companyUsers")
    public String listUsersByCompany(@RequestParam int companyId, Model model) {
        Optional<Company> company = companyService.getCompanyById(companyId);
        if (company.isPresent()) {
            model.addAttribute("company", company.get());
            model.addAttribute("users", companyService.getUsersByCompany(companyId));
        }
        return "companyusers";
    }

    // Xóa công ty (đảm bảo user liên quan không bị lỗi)
    @PostMapping("/deleteCompany/{id}")
    public String deleteCompany(@PathVariable int id) {
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }


    @GetMapping("/selectUsersForCompany")
    public String selectUsersForCompany(@RequestParam int companyId, Model model) {
        Optional<Company> companyOptional = companyService.getCompanyById(companyId);
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            model.addAttribute("company", company);

            List<User> availableUsers = userService.getAllUsers().stream()
                    .filter(user -> user.getCompany() == null || user.getCompany().getId() != companyId)
                    .collect(Collectors.toList());

            model.addAttribute("users", availableUsers);
        }
        return "selectusers";
    }

    // Xử lý thêm user vào công ty
    @PostMapping("/addUsersToCompany")
    public String addUsersToCompany(@RequestParam int companyId, @RequestParam List<Integer> userIds) {
        Optional<Company> companyOptional = companyService.getCompanyById(companyId);
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            List<User> users = userService.getAllUsers().stream()
                    .filter(user -> userIds.contains(user.getId()))
                    .collect(Collectors.toList());

            for (User user : users) {
                user.setCompany(company);
                userService.saveUser(user, companyId);
            }
        }
        return "redirect:/companyUsers?companyId=" + companyId;
    }
    // Xử lý xóa User khỏi công ty
    @PostMapping("/removeUserFromCompany/{userId}")
    public String removeUserFromCompany(@PathVariable int userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            int companyId = (user.getCompany() != null) ? user.getCompany().getId() : -1;

            // Xóa liên kết với công ty
            user.setCompany(null);
            userService.saveUser(user, null);

            // Chuyển hướng về danh sách User của công ty
            if (companyId != -1) {
                return "redirect:/companyUsers?companyId=" + companyId;
            }
        }
        return "redirect:/companies"; // Nếu không tìm thấy User, quay lại danh sách công ty
    }
}
