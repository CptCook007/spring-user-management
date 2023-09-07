package com.shamal.userregistration.controller;

import com.shamal.userregistration.model.UserInformation;
import com.shamal.userregistration.repository.UserRepository;
import com.shamal.userregistration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/admin/home")
    public String home(Model model,Principal principal){
        model.addAttribute("principalName",principal.getName());
        return "admin/home";
    }
    @GetMapping("/admin/home/admin-panel")
    public String adminPanel(Model model,@RequestParam(name = "search",required = false) String keyword){
        if(keyword!=null) {
            model.addAttribute("users", userService.listUserBy(keyword));
        }
        else {
            model.addAttribute("users", userService.listAllUser());
        }
        return "admin/admin-panel";
    }
    @GetMapping("/admin/home/admin-panel/Update/{id}")
    public String update(Model model , @PathVariable("id") long id){
        model.addAttribute("user",userService.findUserById(id));
        return "admin/update";
    }
    @PostMapping("/admin/home/admin-panel/Update/save/{id}")
    public String saveChanges(Model model,@PathVariable("id") long id,@ModelAttribute UserInformation user){
        userService.updateByID(id,user);
//        model.addAttribute("updated",true);
        return "redirect:/admin/home/admin-panel?update=User+is+updated!";
    }
    @PostMapping("/admin/home/admin-panel/Delete/{id}")
    public String delete(Model model , @PathVariable("id") long id) {
        userService.deleteUser(id);
//        model.addAttribute("user",repository.findById(id).get());
//        model.addAttribute("deleted",true);
        return "redirect:/admin/home/admin-panel?delete=User+deleted!";
    }
    @GetMapping("/admin/home/admin-panel/admin-creation")
    public String adminCreation(){
        return "admin/adminCreate";
    }
    @PostMapping("/admin/home/admin-panel/create-admin")
    public String createAdmin(@ModelAttribute UserInformation user,Model model){
        userService.saveAdmin(user);
        model.addAttribute("adminCreated",true);
        return "redirect:/admin/home/admin-panel?admin=Admin+created!";
    }
}
