package com.slack.api.socket_mode.queue.impl;

import com.slack.api.socket_mode.queue.SocketModeMessageQueue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedMessageQueue implements SocketModeMessageQueue {

    private final ConcurrentLinkedQueue<String> underlying;

    public ConcurrentLinkedMessageQueue() {
        this(new ConcurrentLinkedQueue<>());
    }

    public ConcurrentLinkedMessageQueue(ConcurrentLinkedQueue<String> queue) {
        this.underlying = queue;
    }

    @Override
    public void add(String message) {
        this.underlying.add(message);
    }

    @Override
    public String poll() {
        return this.underlying.poll();
    }
}
