package com.repo.warden.repo_warden.worker;

import com.repo.warden.repo_warden.constant.AppConstant;
import org.springframework.stereotype.Component;

@Component
public class WorkerFactory {
    private final PullRequestSyncWorker pullRequestSyncWorker;

    public WorkerFactory(PullRequestSyncWorker pullRequestSyncWorker) {
        this.pullRequestSyncWorker = pullRequestSyncWorker;
    }

    public Worker getWorker(String worker) {
        if (worker.equals(AppConstant.PR_SYNC_WORKER_NAME)) {
            return pullRequestSyncWorker;
        }
        return null;
    }
}
