package com.repo.warden.repo_warden.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PullRequest {
    private String url;
    private String diff_url;
    private Integer number;
    private String state;
    private String title;
    private String body;
    private String closed_at;
}
