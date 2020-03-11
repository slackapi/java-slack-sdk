package com.slack.api.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExecutorServiceFactory {

    private ExecutorServiceFactory() {
    }

    public static ExecutorService createDaemonThreadPoolExecutor(String threadGroupName, int poolSize) {
        return Executors.newFixedThreadPool(poolSize, new DaemonThreadFactory(threadGroupName));
    }

    public static ScheduledExecutorService createDaemonThreadScheduledExecutor(String threadGroupName) {
        return Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory(threadGroupName));
    }
}
