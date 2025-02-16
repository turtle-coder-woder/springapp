package com.repo.warden.repo_warden.repository;

import com.repo.warden.repo_warden.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}