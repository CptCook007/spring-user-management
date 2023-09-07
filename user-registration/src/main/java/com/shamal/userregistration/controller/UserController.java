package com.shamal.userregistration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {
    @GetMapping("/user/home")
    public String home(Model model, Principal principal){
        model.addAttribute("principalName",principal.getName());
        return "user/home";
    }
}
