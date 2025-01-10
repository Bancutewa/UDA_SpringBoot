package com.example.demo_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/userInfo")
    public String userInfo() {
        return "userInfo";
    }
}
