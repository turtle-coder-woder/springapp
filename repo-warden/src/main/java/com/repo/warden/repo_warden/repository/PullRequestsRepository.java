package com.repo.warden.repo_warden.repository;

import com.repo.warden.repo_warden.model.PullRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PullRequestsRepository extends JpaRepository<PullRequests, Long>{

    List<PullRequests> findByNumberIn(List<Integer> prIds);

    List<PullRequests> findByClosedAtAfter(LocalDateTime localDateTime);
}
