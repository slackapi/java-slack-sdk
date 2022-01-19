package com.slack.api.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Provides ExecutorServices for asynchronous code executions
 */
public interface ExecutorServiceProvider {

    /**
     * Creates a new ExecutorService instance.
     * @param threadGroupName the thread group name
     * @param poolSize the thread pool size
     * @return a new ExecutorService
     */
    ExecutorService createThreadPoolExecutor(String threadGroupName, int poolSize);

    /**
     * Creates a new ScheduledExecutorService instance.
     * @param threadGroupName the thread group name
     * @return a new ScheduledExecutorService
     */
    ScheduledExecutorService createThreadScheduledExecutor(String threadGroupName);

}
