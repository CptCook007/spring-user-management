package com.shamal.userregistration.controller;

import com.shamal.userregistration.model.UserInformation;
import com.shamal.userregistration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class MainController {
    public String AuthRedirect(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            if(roles.contains("ADMIN")){
                return "redirect:/admin/home";
            }
            if(roles.contains("USER")){
                return "redirect:/user/home";
            }
        }
        return "login";
    }
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
            return "redirect:/signup?emailError";
        }
        if(userService.checkUsernameExist(user.getUsername())){
            return "redirect:/signup?userNameError";
        }
        userService.saveUser(user);
        return "redirect:/index?message=success";
    }
    @GetMapping("/login")
    public String login(){
        return AuthRedirect();
    }
//    @GetMapping("/create")
//    public String redirectToHome(){
//        return "redirect:/home";
//    }
    @GetMapping("/index")
    public String home(@RequestParam(name="message",required = false) String param , Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if(roles.contains("ADMIN")){
                return "redirect:/admin/home";
            }
            if(roles.contains("USER")){
                return "redirect:/user/home";
            }
        }
        if(param!=null)
            model.addAttribute("success",true);
        return "index";
    }

    @GetMapping("/")
    public String def(){
        return "redirect:/index";
    }

}
