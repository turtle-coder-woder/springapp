package com.repo.warden.repo_warden.service;

import com.repo.warden.repo_warden.client.GitClient;
import com.repo.warden.repo_warden.constant.AppConstant;
import com.repo.warden.repo_warden.model.GitToken;
import com.repo.warden.repo_warden.model.User;
import com.repo.warden.repo_warden.record.AppMessage;
import com.repo.warden.repo_warden.repository.GitTokenRepository;
import com.repo.warden.repo_warden.service.aws.SqsMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GitTokenService extends CurrentService{

    @Autowired
    private GitClient gitClient;

    @Autowired
    private GitTokenRepository gitTokenRepository;

    @Autowired
    private SqsMessageSender sqsMessageSender;

    public GitTokenService(UserService userService) {
        super(userService);
    }

    public List<GitToken> findAll() {
        return gitTokenRepository.findAll();
    }

    public Optional<GitToken> findById(Long id) {
        return gitTokenRepository.findById(id);
    }

    public GitToken save(GitToken gitToken) {
        Map response;
        try {
            response = gitClient.getRepoDetails(gitToken.getOrg(),gitToken.getRepo(),gitToken.getPat()).block();
        } catch (Exception e) {
            throw new RuntimeException("Invalid Git Token");
        }
        GitToken gitTokenSaved = gitTokenRepository.save(gitToken);
        //send mesasge to sqs
        sqsMessageSender.sendMessage(new AppMessage(AppConstant.PR_SYNC_WORKER_NAME, currentUser().getEmail()));
        return gitTokenSaved;
    }

    public void deleteById(Long id) {
        gitTokenRepository.deleteById(id);
    }

    public GitToken findByUser(Long userId) {
        return gitTokenRepository.findByUserId(userId);
    }

    public GitToken findByCurrentUser() {
        User user = currentUser();
        if(user==null) return null;
        return gitTokenRepository.findByUserId(user.getId());
    }
}