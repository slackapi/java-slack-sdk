package com.slack.api.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

// Use ExecutorServiceProvider interface instead
@Deprecated
public class ExecutorServiceFactory extends DaemonThreadExecutorServiceFactory {

    private ExecutorServiceFactory() {
    }
}
