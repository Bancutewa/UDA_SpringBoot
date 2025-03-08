//package com.example.demo_spring.controller;
//
//import com.example.demo_spring.model.Company;
//import com.example.demo_spring.model.User;
//import com.example.demo_spring.service.CompanyService;
//import com.example.demo_spring.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//public class UserController {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    private final UserService userService;
//    private final CompanyService companyService;
//
//
//    public UserController(UserService userService, CompanyService companyService) {
//        this.userService = userService;
//        this.companyService = companyService;
//    }
//
//
//    @GetMapping("/register")
//    public String showRegisterForm(Model model) {
//        model.addAttribute("user", new User());
//        return "register"; // Trả về trang đăng ký (register.html)
//    }
//
//    @PostMapping("/register")
//    public String register(@ModelAttribute User user) {
//        userService.registerUser(user.getName(), user.getClassSchool(),user.getPhone(),user.getEmail(),user.getImgURL(), user.getPassword(), "USER");
//
//        return "redirect:/login"; // Sau khi đăng ký thành công, chuyển hướng đến trang đăng nhập
//    }
//
//    @GetMapping("/login")
//    public String showLoginForm() {
//        System.out.println("📌 Truy cập trang đăng nhập");
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestParam String email, @RequestParam String password, Model model) {
//        System.out.println("📌 Đang xử lý đăng nhập...");
//        System.out.println("📩 Email nhập vào: " + email);
//        System.out.println("🔑 Mật khẩu nhập vào: " + password);
//
//        if (email == null || email.isEmpty()) {
//            System.out.println("❌ Lỗi: Email rỗng!");
//            model.addAttribute("error", "Email không được để trống!");
//            return "login";
//        }
//
//        try {
//            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
//            System.out.println("📌 Đang gọi authenticationManager.authenticate()...");
//            Authentication authentication = authenticationManager.authenticate(authRequest);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            System.out.println("✅ Đăng nhập thành công: " + email);
//            return "redirect:/home";
//        } catch (Exception e) {
//            System.out.println("❌ Đăng nhập thất bại: " + e.getMessage());
//            model.addAttribute("error", "Đăng nhập thất bại! Kiểm tra email và mật khẩu.");
//            return "login";
//        }
//    }
//
//    @GetMapping("/home")
//    public String index(Model model) {
//        List<User> users = userService.getAllUsers();
//        model.addAttribute("users", users);
//        return "index";
//    }
//
//    // Xem thông tin chi tiết người dùng
//    @GetMapping("/userInfo/{id}")
//    public String userInfo(@PathVariable("id") int id, Model model) {
//        Optional<User> optionalUser = userService.getUserById(id);
//        if (optionalUser.isEmpty()) {
//            throw new RuntimeException("User not found!");
//        }
//        User user = optionalUser.get();
//        model.addAttribute("user", user);
//        return "userInfo";
//    }
//
//    // Hiển thị form thêm người dùng (có danh sách Company để chọn)
//    @GetMapping("/addUser")
//    public String showAddUserForm(Model model) {
//        model.addAttribute("user", new User());
//        model.addAttribute("companies", companyService.getAllCompanies()); // Lấy danh sách công ty
//        return "addUser";
//    }
//
//    // Xử lý thêm người dùng vào Company
//    @PostMapping("/addUser")
//    public String addUser(@ModelAttribute("user") User user, @RequestParam("companyId") int companyId) {
//        Optional<Company> optionalCompany = companyService.getCompanyById(companyId);
//        optionalCompany.ifPresent(user::setCompany); // Gán Company cho User nếu có
//        userService.addUser(user);
//        return "redirect:/home";
//    }
//
//    // Hiển thị form chỉnh sửa người dùng
//    @GetMapping("/editUser/{id}")
//    public String editUser(@PathVariable("id") int id, Model model) {
//        Optional<User> optionalUser = userService.getUserById(id);
//        if (optionalUser.isEmpty()) {
//            throw new RuntimeException("User not found!");
//        }
//        User user = optionalUser.get();
//        model.addAttribute("user", user);
//        model.addAttribute("companies", companyService.getAllCompanies()); // Lấy danh sách công ty
//        return "editUser";
//    }
//
//    // Xử lý cập nhật người dùng (bao gồm Company)
//    @PostMapping("/editUser/{id}")
//    public String updateUser(@PathVariable("id") int id, @ModelAttribute User updatedUser, @RequestParam("companyId") int companyId) {
//        Optional<Company> optionalCompany = companyService.getCompanyById(companyId);
//        optionalCompany.ifPresent(updatedUser::setCompany); // Gán Company cho User nếu có
//        boolean isUpdated = userService.updateUser(id, updatedUser);
//        if (!isUpdated) {
//            throw new RuntimeException("User not found!");
//        }
//        return "redirect:/userInfo/" + id;
//    }
//
//    // Xóa người dùng
//    @PostMapping("/deleteUser/{id}")
//    public String deleteUser(@PathVariable("id") int id) {
//        boolean isDeleted = userService.deleteUser(id);
//        if (!isDeleted) {
//            throw new RuntimeException("User not found for deletion!");
//        }
//        return "redirect:/home";
//    }
//}
