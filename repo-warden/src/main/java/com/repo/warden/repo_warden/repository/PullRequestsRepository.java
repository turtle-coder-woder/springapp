package com.repo.warden.repo_warden.repository;

import com.repo.warden.repo_warden.model.PullRequests;
import com.repo.warden.repo_warden.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PullRequestsRepository extends JpaRepository<PullRequests, Long>{

    List<PullRequests> findByClosedAtAfterAndUser(LocalDateTime localDateTime, User user);

    List<PullRequests> findByNumberInAndUser(List<Integer> prIds, User user);
}
