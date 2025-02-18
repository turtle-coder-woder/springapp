package com.repo.warden.repo_warden.worker;

import com.repo.warden.repo_warden.record.AppMessage;
import com.repo.warden.repo_warden.service.CurrentService;
import com.repo.warden.repo_warden.service.UserService;

public abstract class Worker extends CurrentService {
    public Worker(UserService userService) {
        super(userService);
    }

    public abstract void perform(AppMessage message);
}
