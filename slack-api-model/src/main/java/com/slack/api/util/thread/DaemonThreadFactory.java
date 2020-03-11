package com.slack.api.util.thread;

import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory {
    private final ThreadGroup threadGroup;

    public DaemonThreadFactory(String threadGroupName) {
        this.threadGroup = new ThreadGroup(threadGroupName);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(threadGroup, r);
        t.setDaemon(true);
        t.setName(t.getThreadGroup().getName() + "-worker-" + t.getId());
        return t;
    }
}
