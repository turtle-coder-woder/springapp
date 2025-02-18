package com.repo.warden.repo_warden.service;

import com.repo.warden.repo_warden.model.User;
import com.repo.warden.repo_warden.record.AppMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentService {
    public final UserService userService;

    public CurrentService(UserService userService) {
        this.userService = userService;
    }

    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userService.findByEmail(userDetails.getUsername());
        }
        return null;
    }
}
