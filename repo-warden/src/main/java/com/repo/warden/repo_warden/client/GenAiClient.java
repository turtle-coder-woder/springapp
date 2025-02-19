package com.repo.warden.repo_warden.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.repo.warden.repo_warden.client.filter.WebClientLoggingFilter;
import com.repo.warden.repo_warden.pojo.genai.rca.GenerateContentRCADetailsRequest;
import com.repo.warden.repo_warden.pojo.genai.rca.GenerateContentRequest;
import com.repo.warden.repo_warden.pojo.genai.rca.GenerateContentResponse;
import com.repo.warden.repo_warden.pojo.genai.rca.ResponseDetailsRCA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class GenAiClient {

    private final WebClient webClient;

    @Value("${gen.ai.apikey}")
    private String apiKey;

    public GenAiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro")
                .build();
    }

    public Mono<GenerateContentResponse> generateContent(GenerateContentRequest request) {
        printRequest(request);
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path(":generateContent").queryParam("key", apiKey).build())
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GenerateContentResponse.class);
    }

    public Mono<ResponseDetailsRCA> generateContentDetails(GenerateContentRCADetailsRequest request) {
        printRequest(request);
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path(":generateContent").queryParam("key", apiKey).build())
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ResponseDetailsRCA.class);
    }

    private void printRequest(GenerateContentRCADetailsRequest request) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(request);
            log.info("Request: {}", jsonRequest);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void printRequest(GenerateContentRequest request) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(request);
            log.info("Request: {}", jsonRequest);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}