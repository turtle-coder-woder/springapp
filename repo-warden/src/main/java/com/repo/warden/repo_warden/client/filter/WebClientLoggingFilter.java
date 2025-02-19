package com.repo.warden.repo_warden.client.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;

@Slf4j
public class WebClientLoggingFilter {

    private static final Logger logger = LoggerFactory.getLogger(WebClientLoggingFilter.class);

    public static void logRequest(ClientRequest request) {
        log.info("Request: {} {}", request.method(), request.url());
        request.headers().forEach((name, values) -> values.forEach(value -> logger.info("{}={}", name, value)));
    }

    public static void logResponse(ClientResponse response) {
        log.info("Response Status: {}", response.statusCode());
        response.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> logger.info("{}={}", name, value)));
    }
}