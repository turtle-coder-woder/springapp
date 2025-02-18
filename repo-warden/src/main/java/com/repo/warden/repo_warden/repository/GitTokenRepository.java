package com.repo.warden.repo_warden.repository;

import com.repo.warden.repo_warden.model.GitToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GitTokenRepository extends JpaRepository<GitToken, Long> {
    GitToken findByUserId(Long userId);
}
