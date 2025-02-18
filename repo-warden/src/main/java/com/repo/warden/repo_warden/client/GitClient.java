package com.repo.warden.repo_warden.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class GitClient {

    private final WebClient webClient;

    public GitClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.github.com").build();
    }

    public Mono<Map> getRepoDetails(String owner, String repo, String token) {
        return webClient.get()
                .uri("repos/{owner}/{repo}", owner, repo)
                .header("Authorization", "Bearer "+token)
                .retrieve()
                .bodyToMono(Map.class);
    }
}
