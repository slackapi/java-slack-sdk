package com.slack.api.methods.impl;

import com.slack.api.methods.MethodsConfig;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

public class ThreadPools {

    // Executor Name -> Executor
    private static final ConcurrentMap<String, ExecutorService> ALL_DEFAULT = new ConcurrentHashMap<>();
    // Executor Name -> Team ID -> Executor
    private static final ConcurrentMap<String, ConcurrentMap<String, ExecutorService>> TEAM_CUSTOM = new ConcurrentHashMap<>();

    private ThreadPools() {
    }

    public static ExecutorService getDefault(MethodsConfig config) {
        return getOrCreate(config, null);
    }

    public static ExecutorService getOrCreate(MethodsConfig config, String teamId) {
        String executorName = config.getExecutorName();
        Integer customPoolSize = teamId != null ? config.getCustomThreadPoolSizes().get(teamId) : null;
        if (customPoolSize != null) {
            ConcurrentMap<String, ExecutorService> allTeams = TEAM_CUSTOM.get(executorName);
            if (allTeams == null) {
                allTeams = new ConcurrentHashMap<>();
                TEAM_CUSTOM.put(executorName, allTeams);
            }
            ExecutorService teamExecutor = allTeams.get(teamId);
            if (teamExecutor == null) {
                String threadGroupName = "slack-methods-" + config.getExecutorName() + "-" + teamId;
                teamExecutor = config.getExecutorServiceProvider().createThreadPoolExecutor(threadGroupName, customPoolSize);
                allTeams.put(teamId, teamExecutor);
            }
            return teamExecutor;

        } else {
            ExecutorService defaultExecutor = ALL_DEFAULT.get(executorName);
            if (defaultExecutor == null) {
                String threadGroupName = "slack-methods-" + config.getExecutorName();
                int poolSize = config.getDefaultThreadPoolSize();
                defaultExecutor = config.getExecutorServiceProvider().createThreadPoolExecutor(threadGroupName, poolSize);
                ALL_DEFAULT.put(config.getExecutorName(), defaultExecutor);
            }
            return defaultExecutor;
        }
    }

}
