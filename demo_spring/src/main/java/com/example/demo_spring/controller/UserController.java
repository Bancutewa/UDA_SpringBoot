package com.example.demo_spring.controller;

import com.example.demo_spring.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @GetMapping("/userInfo")
    public String userInfo(Model model) {
        User user = new User(1, "Tran Ngoc Tien", "ST22D", "0904715983", "tranngoctien10304@gmail.com","https://scontent.fhan20-1.fna.fbcdn.net/v/t39.30808-6/432469005_1774739339714097_4449507038576999807_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=bti5Qst1GcwQ7kNvgHY3HJr&_nc_oc=Adj__b4RsIZn2wIJg2X_4D-thh_ui8pB31yJs_yFS8Kef-QsCr13ryZJUcYKYh7eMIM&_nc_zt=23&_nc_ht=scontent.fhan20-1.fna&_nc_gid=AYGi9-TDLu5ALDS5dSQIPLK&oh=00_AYBGvCWVGBrAD2LJMw1WmjhnJlzjkR1RW70ITrWxCwfrPw&oe=67872720");
        model.addAttribute("user", user);
        return "userInfo";
    }


}
