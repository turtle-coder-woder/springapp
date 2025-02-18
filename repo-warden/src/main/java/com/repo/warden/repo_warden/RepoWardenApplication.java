package com.repo.warden.repo_warden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RepoWardenApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepoWardenApplication.class, args);
	}

}
