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

    /**
     * Returns the unique identifier for this instance. The value must be unique among different implementations.
     * The default implementation is exactly the same with the default #toString() method.
     * The reason why we have this method is to avoid unexpected side effect
     * in the case where a developer customizes the #toString() method behavior.
     * @return an instance ID
     */
    default String getInstanceId() {
        return this.getClass().getName() + "@" + Integer.toHexString(this.hashCode());
    }

}
