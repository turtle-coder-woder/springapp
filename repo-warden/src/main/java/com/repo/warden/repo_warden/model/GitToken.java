package com.repo.warden.repo_warden.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "git_tokens")
public class GitToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @Column(nullable = false)
    public String pat;

    @Column(nullable = false)
    public String org;

    @Column(nullable = false)
    public String repo;

    @CreationTimestamp
    @Column(nullable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    public LocalDateTime updatedAt;


    // Getters and Setters
}