package com.repo.warden.repo_warden.client;

import com.repo.warden.repo_warden.model.GitToken;
import com.repo.warden.repo_warden.pojo.PullRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GitClientFacade {
    private final GitClient gitClient;

    public GitClientFacade(GitClient gitClient) {
        this.gitClient = gitClient;
    }

    public List<PullRequest> getPullRequests(GitToken gitToken) {
        log.debug("getting pull requests");
        return gitClient.getPullRequests(gitToken.getOrg(),gitToken.getRepo(), gitToken.getPat()).collectList().block();
    }

    public String getDiff(GitToken token, PullRequest pullRequest) {
        return gitClient.getDiff(token.getOrg(), token.getRepo(), token.getPat(), pullRequest.getNumber()).block();
    }
}
