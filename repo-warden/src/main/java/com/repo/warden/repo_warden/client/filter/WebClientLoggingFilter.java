package com.repo.warden.repo_warden.client.filter;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.reactive.function.client.ClientResponse;
    import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
    import reactor.core.publisher.Mono;

    @Slf4j
    public class WebClientLoggingFilter {

        public static ExchangeFilterFunction logRequest() {
            return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
                clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
                return Mono.just(clientRequest);
            });
        }

        public static ExchangeFilterFunction logResponse() {
            return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                log.info("Response status: {}", clientResponse.statusCode());
                clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
                return clientResponse.bodyToMono(String.class)
                        .flatMap(body -> {
                            log.info("Response body: {}", body);
                            return Mono.just(clientResponse);
                        });
            });
        }
    }