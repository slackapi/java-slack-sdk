package test_locally.api.socket_mode;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.socket_mode.SocketModeClient;
import com.slack.api.socket_mode.SocketModeMessage;
import com.slack.api.socket_mode.listener.WebSocketMessageListener;
import com.slack.api.socket_mode.queue.SocketModeMessageQueue;
import com.slack.api.socket_mode.queue.impl.ConcurrentLinkedMessageQueue;
import com.slack.api.socket_mode.queue.impl.TimedMessageQueue;
import com.slack.api.util.thread.ExecutorServiceProvider;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class SocketModeMessageQueueTest {
    @Test
    public void receivesEachDeliverySeparatelyAndPreservesFifo() {
        SocketModeMessageQueue queue = new TimedMessageQueue();
        long before = System.nanoTime();
        queue.add("same payload");
        long between = System.nanoTime();
        queue.add("same payload");
        long after = System.nanoTime();

        SocketModeMessage first = queue.pollMessage();
        SocketModeMessage second = queue.pollMessage();
        assertEquals("same payload", first.getPayload());
        assertEquals("same payload", second.getPayload());
        assertTrue(first.getReceivedAtNanos() >= before);
        assertTrue(first.getReceivedAtNanos() <= between);
        assertTrue(second.getReceivedAtNanos() >= between);
        assertTrue(second.getReceivedAtNanos() <= after);
        assertNull(queue.pollMessage());
    }

    @Test
    public void existingStringPollRemainsSupported() {
        SocketModeMessageQueue queue = new TimedMessageQueue();
        queue.add("first");
        queue.add("second");
        assertEquals("first", queue.poll());
        assertEquals("second", queue.pollMessage().getPayload());
        assertNull(queue.poll());
    }

    @Test
    public void existingQueuesReportUnknownTimingRatherThanZeroWait() {
        SocketModeMessageQueue queue = new ConcurrentLinkedMessageQueue();
        queue.add("legacy");
        SocketModeMessage message = queue.pollMessage();
        assertEquals("legacy", message.getPayload());
        assertNull(message.getReceivedAtNanos());
        assertNull(queue.pollMessage());
    }

    @Test
    public void dispatcherPreservesOverridesAndClearsTimingAfterFailure() throws Exception {
        Dispatcher dispatcher = new Dispatcher();
        SocketModeClient client = dispatcher.client;
        String payload = "{\"type\":\"hello\"}";
        doThrow(new IOException("Failed overridden handler")).doCallRealMethod().when(client).processMessage(payload);
        client.enqueueMessage(payload);
        dispatcher.poll.run();
        verify(client).processMessage(payload);
        assertNull(dispatcher.received.get());
        assertNull(dispatcher.rawMessage.get());

        // A direct dispatch must not inherit the failed override's receive time.
        client.processMessage(payload);
        assertNull(dispatcher.received.get().getReceivedAtNanos());
        assertEquals(payload, dispatcher.rawMessage.get());

        // An override delegating to the default implementation retains queued timing.
        client.enqueueMessage(payload);
        dispatcher.poll.run();
        verify(client, times(3)).processMessage(payload);
        assertNotNull(dispatcher.received.get().getReceivedAtNanos());
        assertEquals(payload, dispatcher.rawMessage.get());

        // Timing is also cleared after a successful dispatch.
        client.processMessage(payload);
        assertNull(dispatcher.received.get().getReceivedAtNanos());
        assertEquals(payload, dispatcher.received.get().getPayload());
    }

    @Test
    public void nestedDirectDispatchDoesNotInheritQueuedTiming() throws Exception {
        Dispatcher dispatcher = new Dispatcher();
        String payload = "{\"type\":\"hello\"}";
        String nestedPayload = "{\"type\":\"hello\",\"nested\":true}";
        AtomicReference<SocketModeMessage> queued = new AtomicReference<>();
        doAnswer(invocation -> {
            invocation.callRealMethod();
            queued.set(dispatcher.received.get());
            // The override is still running, but this is a separate direct delivery.
            dispatcher.client.processMessage(nestedPayload);
            return null;
        }).when(dispatcher.client).processMessage(payload);

        dispatcher.client.enqueueMessage(payload);
        dispatcher.poll.run();

        assertNotNull(queued.get().getReceivedAtNanos());
        assertEquals(payload, queued.get().getPayload());
        assertEquals(nestedPayload, dispatcher.received.get().getPayload());
        assertNull(dispatcher.received.get().getReceivedAtNanos());
    }

    private static class Dispatcher {
        private final SocketModeClient client = mock(SocketModeClient.class, CALLS_REAL_METHODS);
        private final AtomicReference<SocketModeMessage> received = new AtomicReference<>();
        private final AtomicReference<String> rawMessage = new AtomicReference<>();
        private final Runnable poll;

        Dispatcher() {
            ScheduledExecutorService executor = mock(ScheduledExecutorService.class);
            ExecutorServiceProvider provider = mock(ExecutorServiceProvider.class);
            when(provider.createThreadScheduledExecutor(anyString())).thenReturn(executor);
            SlackConfig config = new SlackConfig();
            config.setExecutorServiceProvider(provider);
            Slack slack = mock(Slack.class);
            when(slack.getConfig()).thenReturn(config);
            when(client.getSlack()).thenReturn(slack);
            when(client.getMessageQueue()).thenReturn(new TimedMessageQueue());
            WebSocketMessageListener listener = new WebSocketMessageListener() {
                @Override
                public void handle(String message) {
                    fail("The dispatcher should supply the message metadata");
                }

                @Override
                public void handleWithMetadata(SocketModeMessage message) {
                    received.set(message);
                }
            };
            when(client.getWebSocketMessageListeners()).thenReturn(Arrays.asList(listener, rawMessage::set));
            client.initializeMessageProcessorExecutor(1);
            ArgumentCaptor<Runnable> task = ArgumentCaptor.forClass(Runnable.class);
            verify(executor).scheduleAtFixedRate(task.capture(), eq(0L), eq(10L), eq(TimeUnit.MILLISECONDS));
            poll = task.getValue();
        }
    }
}
