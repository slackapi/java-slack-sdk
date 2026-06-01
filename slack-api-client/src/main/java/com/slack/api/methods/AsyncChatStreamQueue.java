package com.slack.api.methods;

import com.slack.api.methods.response.chat.ChatAppendStreamResponse;
import com.slack.api.methods.response.chat.ChatStopStreamResponse;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

class AsyncChatStreamQueue {

    private CompletableFuture<Void> pending = CompletableFuture.completedFuture(null);
    private boolean flushQueued;
    private CompletableFuture<ChatAppendStreamResponse> queuedFlush;
    private boolean stopRequested;

    synchronized boolean isStopRequested() {
        return stopRequested;
    }

    synchronized void clearStopRequested() {
        stopRequested = false;
    }

    CompletableFuture<ChatAppendStreamResponse> enqueueFlush(
            Supplier<CompletableFuture<ChatAppendStreamResponse>> work
    ) {
        CompletableFuture<Void> currentPending;
        synchronized (this) {
            if (flushQueued) {
                return queuedFlush;
            }
            flushQueued = true;
            currentPending = pending;
        }
        CompletableFuture<ChatAppendStreamResponse> result = currentPending.thenCompose(ignored -> {
            clearQueuedFlush();
            return work.get();
        });
        synchronized (this) {
            queuedFlush = result;
            pending = result.handle((resp, error) -> null);
        }
        return result;
    }

    CompletableFuture<ChatStopStreamResponse> enqueueStop(
            Supplier<CompletableFuture<ChatStopStreamResponse>> work
    ) {
        CompletableFuture<Void> currentPending;
        synchronized (this) {
            stopRequested = true;
            currentPending = pending;
        }
        CompletableFuture<ChatStopStreamResponse> result = currentPending.thenCompose(ignored -> work.get());
        synchronized (this) {
            pending = result.handle((resp, error) -> null);
        }
        return result;
    }

    private synchronized void clearQueuedFlush() {
        flushQueued = false;
        queuedFlush = null;
    }
}
