package com.repo.warden.repo_warden.controller;

import com.repo.warden.repo_warden.model.GitToken;
import com.repo.warden.repo_warden.model.User;
import com.repo.warden.repo_warden.service.CustomUserDetails;
import com.repo.warden.repo_warden.service.GitTokenService;
import com.repo.warden.repo_warden.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class MyController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    GitTokenService gitTokenService;


    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About Page");
        return "about";
    }

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute("title", "Home Page");
        if(gitTokenService.findByCurrentUser() == null){
            return "pat";
        }

        return "home";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Signup");
        model.addAttribute("hideNavbar", true);
        return "signup";
    }


    @GetMapping("/login-me")
    public String handleLogin(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("hideNavbar", true);
        return "login";
    }

    @PostMapping("/signup")
    public String handleLogin(Model model,
                              @RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpSession session) {
        User existingUser = userService.findByEmail(email);
        if (existingUser == null) {
            // Sign up
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            userService.saveUser(newUser);
            return "redirect:/";
        }

        return "redirect:/signup?error";
    }

    @PostMapping("/submit-pat")
    public String submitPat(@RequestParam("pat") String pat,
                            @RequestParam("org") String org,
                            @RequestParam("repo") String repo,
                            HttpSession session) {
        User currentUser = gitTokenService.currentUser();
        if (currentUser != null) {
            GitToken gitToken = GitToken.builder().user(currentUser).pat(pat).repo(repo).org(org).build();
            gitTokenService.save(gitToken);
        }
        return "redirect:/";
    }
}