package com.slack.api.audit.impl;

import com.slack.api.audit.AuditConfig;

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

    public static ExecutorService getDefault(AuditConfig config) {
        return getOrCreate(config, null);
    }

    public static ExecutorService getOrCreate(AuditConfig config, String enterpriseId) {
        String providerInstanceId = config.getExecutorServiceProvider().toString();
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

    private static ExecutorService buildNewExecutorService(AuditConfig config) {
        String threadGroupName = "slack-audit-logs-" + config.getExecutorName();
        int poolSize = config.getDefaultThreadPoolSize();
        return config.getExecutorServiceProvider().createThreadPoolExecutor(threadGroupName, poolSize);
    }

    private static ExecutorService buildNewExecutorService(
            AuditConfig config, String enterpriseId, Integer customPoolSize) {
        String threadGroupName = "slack-audit-logs-" + config.getExecutorName() + "-" + enterpriseId;
        return config.getExecutorServiceProvider().createThreadPoolExecutor(threadGroupName, customPoolSize);
    }

}
