package com.repo.warden.repo_warden.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MyController {

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About Page");
        return "about";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home Page");
        return "home";
    }
}
