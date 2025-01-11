package com.example.demo_spring.controller;

import com.example.demo_spring.model.User;
import com.example.demo_spring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

    // Hiển thị form thêm người dùng
    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    // Xử lý thêm người dùng
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        User newuser = userService.addUser(user);
        System.out.println(newuser);
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
        return "editUser";
    }

    // Xử lý cập nhật người dùng
    @PostMapping("/editUser/{id}")
    public String updateUser(@PathVariable("id") int id, @ModelAttribute User updatedUser) {
        boolean isUpdated = userService.updateUser(id, updatedUser);
        if (!isUpdated) {
            throw new RuntimeException("User not found!");
        }
        System.out.println(updatedUser);
        return "redirect:/userInfo/" + id;
    }

    // Xử lý xóa người dùng
    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        boolean isDeleted = userService.deleteUser(id);
        if (!isDeleted) {
            throw new RuntimeException("User not found for deletion!");
        }
        return "redirect:/home";
    }
}
