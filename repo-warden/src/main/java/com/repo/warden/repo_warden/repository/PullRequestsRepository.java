package com.repo.warden.repo_warden.repository;

import com.repo.warden.repo_warden.model.PullRequests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PullRequestsRepository extends JpaRepository<PullRequests, Long>{

}
