package com.repo.warden.repo_warden.client;

import com.repo.warden.repo_warden.pojo.PullRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

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

    public Flux<PullRequest> getPullRequests(String org, String repo, String pat) {
        // support query params for ?state=closed&per_page=1&page=2
        int page = 1;
        int perPage = 100;
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("repos/{owner}/{repo}/pulls")
                        .queryParam("state", "closed")
                        .queryParam("per_page", perPage)
                        .queryParam("page", page)
                        .build(org, repo))
                .header("Authorization", "Bearer "+pat)
                .retrieve()
                .bodyToFlux(PullRequest.class);
    }

    public Mono<String> getDiff(String org, String repo, String pat, Integer number) {
        return webClient.get()
                .uri("https://patch-diff.githubusercontent.com/raw/{owner}/{repo}/pull/{number}.diff", org, repo, number)
                .header("Authorization", "Bearer "+pat)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(60))
//                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(5)))
                .doOnError(e -> System.out.println("Error: " + e.getMessage()));
    }
}
