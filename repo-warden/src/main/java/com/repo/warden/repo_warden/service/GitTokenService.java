package com.repo.warden.repo_warden.service;

import com.repo.warden.repo_warden.model.GitToken;
import com.repo.warden.repo_warden.repository.GitTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GitTokenService {

    @Autowired
    private GitTokenRepository gitTokenRepository;

    public List<GitToken> findAll() {
        return gitTokenRepository.findAll();
    }

    public Optional<GitToken> findById(Long id) {
        return gitTokenRepository.findById(id);
    }

    public GitToken save(GitToken gitToken) {
        return gitTokenRepository.save(gitToken);
    }

    public void deleteById(Long id) {
        gitTokenRepository.deleteById(id);
    }

    public GitToken findByUser(Long userId) {
        return gitTokenRepository.findByUserId(userId);
    }
}