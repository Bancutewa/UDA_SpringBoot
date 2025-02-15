package com.example.demo_spring.controller;

import com.example.demo_spring.model.Company;
import com.example.demo_spring.model.User;
import com.example.demo_spring.service.CompanyService;
import com.example.demo_spring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;
    private final CompanyService companyService;

    public UserController(UserService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    @GetMapping("/home")
    public String index(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

    // Xem thông tin chi tiết người dùng
    @GetMapping("/userInfo/{id}")
    public String userInfo(@PathVariable("id") int id, Model model) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }
        User user = optionalUser.get();
        model.addAttribute("user", user);
        return "userInfo";
    }

    // Hiển thị form thêm người dùng (có danh sách Company để chọn)
    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("companies", companyService.getAllCompanies()); // Lấy danh sách công ty
        return "addUser";
    }

    // Xử lý thêm người dùng vào Company
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("companyId") int companyId) {
        Optional<Company> optionalCompany = companyService.getCompanyById(companyId);
        optionalCompany.ifPresent(user::setCompany); // Gán Company cho User nếu có
        userService.addUser(user);
        return "redirect:/home";
    }

    // Hiển thị form chỉnh sửa người dùng
    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }
        User user = optionalUser.get();
        model.addAttribute("user", user);
        model.addAttribute("companies", companyService.getAllCompanies()); // Lấy danh sách công ty
        return "editUser";
    }

    // Xử lý cập nhật người dùng (bao gồm Company)
    @PostMapping("/editUser/{id}")
    public String updateUser(@PathVariable("id") int id, @ModelAttribute User updatedUser, @RequestParam("companyId") int companyId) {
        Optional<Company> optionalCompany = companyService.getCompanyById(companyId);
        optionalCompany.ifPresent(updatedUser::setCompany); // Gán Company cho User nếu có
        boolean isUpdated = userService.updateUser(id, updatedUser);
        if (!isUpdated) {
            throw new RuntimeException("User not found!");
        }
        return "redirect:/userInfo/" + id;
    }

    // Xóa người dùng
    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        boolean isDeleted = userService.deleteUser(id);
        if (!isDeleted) {
            throw new RuntimeException("User not found for deletion!");
        }
        return "redirect:/home";
    }
}
