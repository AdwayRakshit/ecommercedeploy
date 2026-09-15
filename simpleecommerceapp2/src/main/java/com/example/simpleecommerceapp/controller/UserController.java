package com.example.simpleecommerceapp.controller;

import com.example.simpleecommerceapp.entity.User;
import com.example.simpleecommerceapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/add/user")
    public String addUser(User user){

        userService.createUser(user);
        return "redirect:/admin/home";
    }

    @GetMapping("/update/user/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user", user);

        return "UpdateUser";
    }

    @PostMapping("/update/user")
    public String updateUser(User user){
        userService.updateUser(user);
        return "redirect:/admin/home";
    }
    @GetMapping("/delete/user/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "redirect:/admin/home";
    }




}
