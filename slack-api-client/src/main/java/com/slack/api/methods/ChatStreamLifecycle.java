package com.slack.api.methods;

class ChatStreamLifecycle {

    enum StreamState {
        STARTING,
        IN_PROGRESS,
        COMPLETED
    }

    private StreamState state = StreamState.STARTING;
    private String streamTs;

    String getStreamTs() {
        return streamTs;
    }

    StreamState getState() {
        return state;
    }

    boolean isStarted() {
        return streamTs != null;
    }

    void verifyAppendable() {
        if (state == StreamState.COMPLETED) {
            throw new SlackChatStreamException("Cannot append to stream: stream state is " + state);
        }
    }

    void verifyStoppable() {
        if (state == StreamState.COMPLETED) {
            throw new SlackChatStreamException("Cannot stop stream: stream state is " + state);
        }
    }

    void markStarted(String streamTs) {
        this.streamTs = streamTs;
        this.state = StreamState.IN_PROGRESS;
    }

    void markCompleted() {
        this.state = StreamState.COMPLETED;
    }
}
