package com.repo.warden.repo_warden.service.genai;

import com.repo.warden.repo_warden.client.GenAiClient;
import com.repo.warden.repo_warden.model.PullRequests;
import com.repo.warden.repo_warden.pojo.genai.rca.GenerateContentRequestRCA;
import com.repo.warden.repo_warden.pojo.genai.rca.GenerateContentResponse;
import com.repo.warden.repo_warden.service.PullRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GenAiService {
    private final GenAiClient genAiClient;
    private final PullRequestService pullRequestService;

    public GenAiService(GenAiClient genAiClient, PullRequestService pullRequestService) {
        this.genAiClient = genAiClient;
        this.pullRequestService = pullRequestService;
    }

    public List<PullRequests> getRCA(){
        String input = "You are an AI assistant that helps identify relevant PRs for a new incident based on subject and description.\n\nA new incident has occurred. Here are the details:\n\nIncident Subject: Database connection timeout\nIncident Description: Users are reporting intermittent failures when accessing the application. The logs indicate frequent timeouts when connecting to the PostgreSQL database.\n\nHere are the recently deployed Pull Requests:\n1. PR ID: 1234\n   PR Subject: Improve database query performance\n   PR Description: Optimized several slow queries to improve response time.\n\n2. PR ID: 5678\n   PR Subject: Refactor API authentication\n   PR Description: Changed token validation mechanism for enhanced security.\n\nBased on the incident description, identify the relevant PRs that are contextual to the incident.";
        GenerateContentResponse response = genAiClient.generateContent(new GenerateContentRequestRCA(input)).block();
        List<Integer> prIds = response.getCandidates().get(0).getContent().getParts().get(0).getFunctionCall().getArgs().getRelevant_prs();
        return pullRequestService.getPrsByExternalId(prIds);
    }
}
