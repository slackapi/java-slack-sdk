package com.slack.api.scim2.impl;

import com.slack.api.scim2.SCIM2Config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

public class ThreadPools {

    // ExecutorServiceProvider ID -> Executor Name -> Executor
    private static final ConcurrentMap<String, ConcurrentMap<String, ExecutorService>> ALL_DEFAULT = new ConcurrentHashMap<>();
    // ExecutorServiceProvider ID -> Executor Name -> Enterprise ID -> Executor
    private static final ConcurrentMap<String, ConcurrentMap<String, ConcurrentMap<String, ExecutorService>>> ENTERPRISE_CUSTOM =
            new ConcurrentHashMap<>();

    private ThreadPools() {
    }

    public static ExecutorService getDefault(SCIM2Config config) {
        return getOrCreate(config, null);
    }

    public static ExecutorService getOrCreate(SCIM2Config config, String enterpriseId) {
        String providerInstanceId = config.getExecutorServiceProvider().getInstanceId();
        Integer orgCustomPoolSize = enterpriseId != null ? config.getCustomThreadPoolSizes().get(enterpriseId) : null;
        if (orgCustomPoolSize != null) {
            return ENTERPRISE_CUSTOM
                    .computeIfAbsent(providerInstanceId, _id -> new ConcurrentHashMap<>())
                    .computeIfAbsent(config.getExecutorName(), _name -> new ConcurrentHashMap<>())
                    .computeIfAbsent(enterpriseId, _id -> buildNewExecutorService(config, enterpriseId, orgCustomPoolSize));
        } else {
            return ALL_DEFAULT
                    .computeIfAbsent(providerInstanceId, _id -> new ConcurrentHashMap<>())
                    .computeIfAbsent(config.getExecutorName(), _name -> buildNewExecutorService(config));
        }
    }

    public static void shutdownAll(SCIM2Config config) {
        String providerInstanceId = config.getExecutorServiceProvider().getInstanceId();
        if (ENTERPRISE_CUSTOM.get(providerInstanceId) != null) {
            for (ConcurrentMap<String, ExecutorService> each : ENTERPRISE_CUSTOM.get(providerInstanceId).values()) {
                for (ExecutorService es : each.values()) {
                    es.shutdownNow();
                }
                each.clear();
            }
            ENTERPRISE_CUSTOM.remove(providerInstanceId);
        }
        if (ALL_DEFAULT.get(providerInstanceId) != null) {
            for (ExecutorService es : ALL_DEFAULT.get(providerInstanceId).values()) {
                es.shutdownNow();
            }
            ALL_DEFAULT.remove(providerInstanceId);
        }
    }

    private static ExecutorService buildNewExecutorService(SCIM2Config config) {
        String threadGroupName = "slack-scim2-" + config.getExecutorName();
        int poolSize = config.getDefaultThreadPoolSize();
        return config.getExecutorServiceProvider().createThreadPoolExecutor(threadGroupName, poolSize);
    }

    private static ExecutorService buildNewExecutorService(
            SCIM2Config config, String enterpriseId, Integer customPoolSize) {
        String threadGroupName = "slack-scim2-" + config.getExecutorName() + "-" + enterpriseId;
        return config.getExecutorServiceProvider().createThreadPoolExecutor(threadGroupName, customPoolSize);
    }

}
