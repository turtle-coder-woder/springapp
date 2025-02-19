package com.repo.warden.repo_warden.service.genai;

import com.repo.warden.repo_warden.client.GenAiClient;
import com.repo.warden.repo_warden.model.PullRequests;
import com.repo.warden.repo_warden.pojo.genai.rca.GenerateContentRequestRCA;
import com.repo.warden.repo_warden.pojo.genai.rca.GenerateContentResponse;
import com.repo.warden.repo_warden.service.PullRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GenAiService {
    private final GenAiClient genAiClient;
    private final PullRequestService pullRequestService;

    public GenAiService(GenAiClient genAiClient, PullRequestService pullRequestService) {
        this.genAiClient = genAiClient;
        this.pullRequestService = pullRequestService;
    }

    public List<PullRequests> getRCA(String incidentSubject, String incidentDescription) {
        String input = prepareInputForRequest(incidentSubject, incidentDescription);
        GenerateContentResponse response = genAiClient.generateContent(new GenerateContentRequestRCA(input)).block();
        List<GenerateContentResponse.Candidate.Content.Part.FunctionCall.Args.RelevantPr> relevantPrs = response.getCandidates().get(0).getContent().getParts().get(0).getFunctionCall().getArgs().getRelevant_prs();
        // return ids
        List<Integer> relevantPrsIds = relevantPrs.stream().map(GenerateContentResponse.Candidate.Content.Part.FunctionCall.Args.RelevantPr::getNumber).collect(Collectors.toList());
        return pullRequestService.getPrsByExternalId(relevantPrsIds);
    }

    private String prepareInputForRequest(String incidentSubject, String incidentDescription) {
        StringBuilder sb = new StringBuilder().append("You are an AI assistant that helps identify relevant PRs for a new incident based on subject and description.\n\nA new incident has occurred. Here are the details:\n\n")
                .append("Incident Subject: ")
                .append(incidentSubject).
                append("\nIncident Description: ")
                .append(incidentDescription)
                .append("\n\nHere are the recently deployed Pull Requests:\n\n")
                .append(getPrsData())
                .append("\n\nBased on the incident description, identify the relevant PRs that are contextual to the incident.");
        return sb.toString();
    }

    private String getPrsData() {
        List<PullRequests> pullRequestsList = pullRequestService.getPrsClosedLastWeek();
        StringBuilder sb = new StringBuilder();
        pullRequestsList.forEach(pr -> {
            sb.append("PR ID: ").append(pr.getNumber()).append("\n   PR Subject: ").append(pr.getTitle()).append("\n   PR Description: ").append(pr.getDescription()).append("\n");
        });
        return sb.toString();
    }
}
