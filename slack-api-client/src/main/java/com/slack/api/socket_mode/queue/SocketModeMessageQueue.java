package com.slack.api.socket_mode.queue;

/**
 * Abstraction of a message queue that provides
 * a way to handle multiple messages at the same time in SocketModeClient.
 */
public interface SocketModeMessageQueue {

    /**
     * Adds a new message from the Socket Mode server.
     *
     * @param message the raw message from the Socket Mode server
     */
    void add(String message);

    /**
     * Pops a message and removes it from the queue.
     *
     * @return a raw message from the Socket Mode server
     */
    String poll();

}
