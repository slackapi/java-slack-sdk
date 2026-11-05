package com.slack.api.bolt.socket_mode.listener;

import com.slack.api.bolt.request.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Consumer;

/**
 * Observes a parsed Socket Mode request immediately before Bolt handles it.
 * Listeners run on the handler thread and should return promptly. Runtime exceptions
 * from listeners are logged without preventing request handling or acknowledgement.
 */
public abstract class SocketModeRequestListener implements Consumer<SocketModeRequestListener.State> {

    public abstract void accept(State state);

    @AllArgsConstructor
    @Data
    public static class State {
        private Request<?> request;
        private String envelopeId;

        /**
         * Monotonic elapsed time from entering the receive queue to starting request handling,
         * in nanoseconds. Includes both SDK queues and dispatch/parsing overhead, but excludes
         * these listeners, Bolt middleware, the handler and acknowledgement sending.
         * Null means the queue or custom transport did not provide timing information.
         */
        private Long dispatchDelayNanos;
    }
}
