package com.slack.api.scim.impl;

import com.slack.api.scim.SCIMConfig;
import com.slack.api.util.thread.ExecutorServiceFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

public class ThreadPools {

    // Executor Name -> Executor
    private static final ConcurrentMap<String, ExecutorService> ALL_DEFAULT = new ConcurrentHashMap<>();
    // Executor Name -> Enterprise ID -> Executor
    private static final ConcurrentMap<String, ConcurrentMap<String, ExecutorService>> ENTERPRISE_CUSTOM =
            new ConcurrentHashMap<>();

    private ThreadPools() {
    }

    public static ExecutorService getDefault(SCIMConfig config) {
        return getOrCreate(config, null);
    }

    public static ExecutorService getOrCreate(SCIMConfig config, String enterpriseId) {
        String executorName = config.getExecutorName();
        Integer customPoolSize = enterpriseId != null ? config.getCustomThreadPoolSizes().get(enterpriseId) : null;
        if (customPoolSize != null) {
            ConcurrentMap<String, ExecutorService> allOrgs = ENTERPRISE_CUSTOM.get(executorName);
            if (allOrgs == null) {
                allOrgs = new ConcurrentHashMap<>();
                ENTERPRISE_CUSTOM.put(executorName, allOrgs);
            }
            ExecutorService orgExecutor = allOrgs.get(enterpriseId);
            if (orgExecutor == null) {
                String threadGroupName = "slack-scim-" + config.getExecutorName() + "-" + enterpriseId;
                orgExecutor = ExecutorServiceFactory.createDaemonThreadPoolExecutor(threadGroupName, customPoolSize);
                allOrgs.put(enterpriseId, orgExecutor);
            }
            return orgExecutor;

        } else {
            ExecutorService defaultExecutor = ALL_DEFAULT.get(executorName);
            if (defaultExecutor == null) {
                String threadGroupName = "slack-scim-" + config.getExecutorName();
                int poolSize = config.getDefaultThreadPoolSize();
                defaultExecutor = ExecutorServiceFactory.createDaemonThreadPoolExecutor(threadGroupName, poolSize);
                ALL_DEFAULT.put(config.getExecutorName(), defaultExecutor);
            }
            return defaultExecutor;
        }
    }

}
