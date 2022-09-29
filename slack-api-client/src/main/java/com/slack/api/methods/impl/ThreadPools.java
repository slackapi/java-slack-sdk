package com.slack.api.methods.impl;

import com.slack.api.methods.MethodsConfig;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

public class ThreadPools {

    // ExecutorServiceProvider ID -> Executor Name -> Executor
    private static final ConcurrentMap<String, ConcurrentMap<String, ExecutorService>> ALL_DEFAULT = new ConcurrentHashMap<>();
    // ExecutorServiceProvider ID -> Executor Name -> Team ID -> Executor
    private static final ConcurrentMap<String, ConcurrentMap<String, ConcurrentMap<String, ExecutorService>>> TEAM_CUSTOM = new ConcurrentHashMap<>();

    private ThreadPools() {
    }

    public static ExecutorService getDefault(MethodsConfig config) {
        return getOrCreate(config, null);
    }

    public static ExecutorService getOrCreate(MethodsConfig config, String teamId) {
        String providerInstanceId = config.getExecutorServiceProvider().getInstanceId();
        Integer teamCustomPoolSize = teamId != null ? config.getCustomThreadPoolSizes().get(teamId) : null;
        if (teamCustomPoolSize != null) {
            return TEAM_CUSTOM
                    .computeIfAbsent(providerInstanceId, _id -> new ConcurrentHashMap<>())
                    .computeIfAbsent(config.getExecutorName(), _name -> new ConcurrentHashMap<>())
                    .computeIfAbsent(teamId, _id -> buildNewExecutorService(config, teamId, teamCustomPoolSize));

        } else {
            return ALL_DEFAULT
                    .computeIfAbsent(providerInstanceId, _id -> new ConcurrentHashMap<>())
                    .computeIfAbsent(config.getExecutorName(), _name -> buildNewExecutorService(config));
        }
    }

    public static void shutdownAll(MethodsConfig config) {
        String providerInstanceId = config.getExecutorServiceProvider().getInstanceId();
        if (TEAM_CUSTOM.get(providerInstanceId) != null) {
            for (ConcurrentMap<String, ExecutorService> each : TEAM_CUSTOM.get(providerInstanceId).values()) {
                for (ExecutorService es : each.values()) {
                    es.shutdownNow();
                }
                each.clear();
            }
            TEAM_CUSTOM.remove(providerInstanceId);
        }
        if (ALL_DEFAULT.get(providerInstanceId) != null) {
            for (ExecutorService es : ALL_DEFAULT.get(providerInstanceId).values()) {
                es.shutdownNow();
            }
            ALL_DEFAULT.remove(providerInstanceId);
        }
    }

    private static ExecutorService buildNewExecutorService(MethodsConfig config) {
        String threadGroupName = "slack-methods-" + config.getExecutorName();
        int poolSize = config.getDefaultThreadPoolSize();
        return config.getExecutorServiceProvider().createThreadPoolExecutor(threadGroupName, poolSize);
    }

    private static ExecutorService buildNewExecutorService(
            MethodsConfig config, String teamId, Integer customPoolSize) {
        String threadGroupName = "slack-methods-" + config.getExecutorName() + "-" + teamId;
        return config.getExecutorServiceProvider().createThreadPoolExecutor(threadGroupName, customPoolSize);
    }

}
