package com.slack.api.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import static com.slack.api.util.thread.DaemonThreadExecutorServiceFactory.createDaemonThreadPoolExecutor;
import static com.slack.api.util.thread.DaemonThreadExecutorServiceFactory.createDaemonThreadScheduledExecutor;

public class DaemonThreadExecutorServiceProvider implements ExecutorServiceProvider {

    private static final DaemonThreadExecutorServiceProvider SINGLETON = new DaemonThreadExecutorServiceProvider();

    public static DaemonThreadExecutorServiceProvider getInstance() {
        return SINGLETON;
    }

    @Override
    public ExecutorService createThreadPoolExecutor(String threadGroupName, int poolSize) {
        return createDaemonThreadPoolExecutor(threadGroupName, poolSize);
    }

    @Override
    public ScheduledExecutorService createThreadScheduledExecutor(String threadGroupName) {
        return createDaemonThreadScheduledExecutor(threadGroupName);
    }

}
