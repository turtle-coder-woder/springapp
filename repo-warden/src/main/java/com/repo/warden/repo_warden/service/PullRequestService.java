package com.repo.warden.repo_warden.service;

import com.repo.warden.repo_warden.client.GitClientFacade;
import com.repo.warden.repo_warden.model.GitToken;
import com.repo.warden.repo_warden.model.PullRequests;
import com.repo.warden.repo_warden.model.User;
import com.repo.warden.repo_warden.pojo.PullRequest;
import com.repo.warden.repo_warden.repository.PullRequestsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class PullRequestService {

    @Autowired
    private GitClientFacade gitClientFacade;


    @Autowired
    private PullRequestsRepository pullRequestsRepository;

    public void syncPrs(GitToken token, User user) {
        log.debug("came to sync prs");
        List<PullRequest> pullRequestsList =  gitClientFacade.getPullRequests(token);
        pullRequestsList.forEach(pullRequest -> {
            String diff = gitClientFacade.getDiff(token, pullRequest);
            log.debug("got diff");
            PullRequests pr = PullRequests.builder().state(pullRequest.getState())
                    .number(pullRequest.getNumber())
                    .title(pullRequest.getTitle())
                    .closedAt(getParsedDateTime(pullRequest.getClosed_at()))
                    .user(user)
                    .diff(diff).build();

            log.debug("saving pr");
            pullRequestsRepository.save(pr);
        });

    }

    private LocalDateTime getParsedDateTime(String closedAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
        return LocalDateTime.parse(closedAt, formatter);
    }


}
