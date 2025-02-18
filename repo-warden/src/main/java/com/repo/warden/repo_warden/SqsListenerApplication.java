package com.repo.warden.repo_warden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Profile("background")
public class SqsListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqsListenerApplication.class, args);
    }
}