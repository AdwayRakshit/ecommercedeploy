
package com.example.simpleecommerceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.simpleecommerceapp.entity.Message;
import com.example.simpleecommerceapp.service.ContactService;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    // Display the Contact Us page
    @GetMapping("/ContactUs")
    public String contactUs(Model model) {
        model.addAttribute("message", new Message());
        return "ContactUs";
    }

    // Handle the submitted contact form
    @PostMapping("/send/message")
    public String sendMessage(
            Message message,
            RedirectAttributes redirectAttributes) {

        contactService.createMessage(message);

        redirectAttributes.addFlashAttribute(
                "confirmation",
                "Your message has been successfully sent!!"
        );

        return "redirect:/ContactUs";
    }
}