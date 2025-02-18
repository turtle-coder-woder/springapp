package com.repo.warden.repo_warden.worker;

import com.repo.warden.repo_warden.client.GitClient;
import com.repo.warden.repo_warden.client.GitClientFacade;
import com.repo.warden.repo_warden.model.GitToken;
import com.repo.warden.repo_warden.model.PullRequests;
import com.repo.warden.repo_warden.model.User;
import com.repo.warden.repo_warden.pojo.PullRequest;
import com.repo.warden.repo_warden.record.AppMessage;
import com.repo.warden.repo_warden.service.CurrentService;
import com.repo.warden.repo_warden.service.GitTokenService;
import com.repo.warden.repo_warden.service.PullRequestService;
import com.repo.warden.repo_warden.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PullRequestSyncWorker extends Worker {


    @Autowired
    private GitTokenService gitTokenService;

    @Autowired
    private PullRequestService pullRequestService;

    public PullRequestSyncWorker(UserService userService) {
        super(userService);
    }

    public void perform(AppMessage message) {
        String email = message.message();
        User user = this.userService.findByEmail(email);
        syncPullRequests(user);
        System.out.println("Syncing pull requests for user: " + user.getEmail());
    }

    private void syncPullRequests(User user) {
        GitToken token = gitTokenService.findByUser(user.getId());
        if (token == null) {
            throw new RuntimeException("No token found for user: " + user.getEmail());
        }
        // Sync pull requests
        pullRequestService.syncPrs(token, user);
    }


}
