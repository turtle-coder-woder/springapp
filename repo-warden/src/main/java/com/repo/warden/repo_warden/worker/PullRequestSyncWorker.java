package com.repo.warden.repo_warden.worker;

import com.repo.warden.repo_warden.model.PullRequests;
import com.repo.warden.repo_warden.model.User;
import com.repo.warden.repo_warden.record.AppMessage;
import com.repo.warden.repo_warden.service.CurrentService;
import com.repo.warden.repo_warden.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class PullRequestSyncWorker extends Worker {


    public PullRequestSyncWorker(UserService userService) {
        super(userService);
    }

    public void perform(AppMessage message) {
        String email = message.message();
        User user = this.userService.findByEmail(email);
        System.out.println("Syncing pull requests for user: " + user.getEmail());
    }

}
