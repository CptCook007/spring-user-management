package com.shamal.userregistration.controller;

import com.shamal.userregistration.model.UserInformation;
import com.shamal.userregistration.repository.UserRepository;
import com.shamal.userregistration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    UserService userService;
    @GetMapping("/signup")
    public String signUp(@RequestParam(name="message" ,required = false) String param , Model model){
        if(param!=null)
            model.addAttribute("error",true);
        return "signup";
    }
    @PostMapping("/create")
    public String createUser(@ModelAttribute UserInformation user){
        if(userService.checkEmailExist(user.getEmail())){
//            model.addAttribute("error",true);
            return "redirect:/signup?message=error";
        }
        userService.saveUser(user);
        return "redirect:/index?message=success";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
//    @GetMapping("/create")
//    public String redirectToHome(){
//        return "redirect:/home";
//    }
    @GetMapping("/index")
    public String home(@RequestParam(name="message",required = false) String param , Model model){
        if(param!=null)
            model.addAttribute("success",true);
        return "index";
    }

    @GetMapping("/")
    public String def(){
        return "redirect:/index";
    }
}
