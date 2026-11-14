package com.slack.api.socket_mode.queue.impl;

import com.slack.api.socket_mode.SocketModeMessage;
import com.slack.api.socket_mode.queue.SocketModeMessageQueue;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/** The default in-memory queue, retaining the receive time with each delivery. */
public class TimedMessageQueue implements SocketModeMessageQueue {
    private final ConcurrentLinkedQueue<SocketModeMessage> messages = new ConcurrentLinkedQueue<>();

    @Override
    public void add(String message) {
        messages.add(new SocketModeMessage(Objects.requireNonNull(message), System.nanoTime()));
    }

    @Override
    public String poll() {
        SocketModeMessage message = pollMessage();
        return message == null ? null : message.getPayload();
    }

    @Override
    public SocketModeMessage pollMessage() {
        return messages.poll();
    }
}
