package test_locally;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.socket_mode.SocketModeClient;
import com.slack.api.socket_mode.impl.SocketModeClientJavaWSImpl;
import com.slack.api.socket_mode.queue.SocketModeMessageQueue;
import com.slack.api.socket_mode.queue.impl.ConcurrentLinkedMessageQueue;
import com.slack.api.socket_mode.queue.impl.TimedMessageQueue;
import com.slack.api.socket_mode.request.SlashCommandsEnvelope;
import com.slack.api.socket_mode.response.AckResponse;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;
import util.socket_mode.MockSocketMode;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class SocketModeTimingTest {
    private static final Gson GSON = GsonFactory.createSnakeCase();

    @Test
    public void receiveTimeSurvivesBothQueuesAndTheSdkStillSendsAcknowledgements() throws Exception {
        try (Fixture fixture = new Fixture(new TimedMessageQueue())) {
            CountDownLatch receiverHeld = new CountDownLatch(1);
            CountDownLatch releaseReceiver = new CountDownLatch(1);
            CountDownLatch handlerHeld = new CountDownLatch(1);
            CountDownLatch releaseHandler = new CountDownLatch(1);
            CountDownLatch secondDispatched = new CountDownLatch(1);
            CountDownLatch secondHandled = new CountDownLatch(1);
            AtomicReference<Request<?>> secondRequest = new AtomicReference<>();
            AtomicReference<Request<?>> observedRequest = new AtomicReference<>();
            AtomicInteger handled = new AtomicInteger();
            AtomicLong secondStarted = new AtomicLong();
            fixture.socket.addWebSocketMessageListener(message -> {
                if (message.contains("first-delivery")) {
                    receiverHeld.countDown();
                    await(releaseReceiver);
                } else if (message.contains("queued-delivery")) {
                    secondDispatched.countDown();
                }
            });
            fixture.app.use((request, response, chain) -> {
                if ("queued-delivery".equals(request.getSocketModeEnvelopeId())) {
                    observedRequest.set(request);
                }
                return chain.next(request);
            });
            fixture.app.command("/hi-socket-mode", (request, context) -> {
                if (handled.getAndIncrement() == 0) {
                    handlerHeld.countDown();
                    await(releaseHandler);
                } else {
                    secondStarted.set(System.nanoTime());
                    secondRequest.set(request);
                    secondHandled.countDown();
                }
                return context.ack();
            });
            try {
                fixture.socketApp.startAsync();
                fixture.enqueueCommand("first-delivery");
                assertTrue(receiverHeld.await(5, TimeUnit.SECONDS));
                long beforeEnqueue = System.nanoTime();
                fixture.enqueueCommand("queued-delivery");
                long afterEnqueue = System.nanoTime();
                releaseReceiver.countDown();
                assertTrue(handlerHeld.await(5, TimeUnit.SECONDS));
                assertTrue(secondDispatched.await(5, TimeUnit.SECONDS));
                assertEquals(1, secondHandled.getCount());
                assertNull(observedRequest.get());
                assertNull(fixture.acknowledgements.poll());
                long handlerReleased = System.nanoTime();
                releaseHandler.countDown();
                assertTrue(secondHandled.await(5, TimeUnit.SECONDS));

                Request<?> request = observedRequest.get();
                assertEquals("queued-delivery", request.getSocketModeEnvelopeId());
                assertSame(secondRequest.get(), request);
                assertNotNull(request.getSocketModeDispatchDelayNanos());
                assertTrue(request.getSocketModeDispatchDelayNanos() >= handlerReleased - afterEnqueue);
                assertTrue(request.getSocketModeDispatchDelayNanos() <= secondStarted.get() - beforeEnqueue);
                assertEquals(new AckResponse("first-delivery"), fixture.acknowledgements.poll(5, TimeUnit.SECONDS));
                assertEquals(new AckResponse("queued-delivery"), fixture.acknowledgements.poll(5, TimeUnit.SECONDS));
            } finally {
                releaseReceiver.countDown();
                releaseHandler.countDown();
            }
        }
    }

    @Test
    public void unknownTimingRemainsAvailableToMiddlewareAndTheHandlerStillAcknowledges() throws Exception {
        try (Fixture fixture = new Fixture(new ConcurrentLinkedMessageQueue())) {
            BlockingQueue<Request<?>> observed = new LinkedBlockingQueue<>();
            AtomicReference<Request<?>> handledRequest = new AtomicReference<>();
            fixture.app.use((request, response, chain) -> {
                observed.add(request);
                return chain.next(request);
            });
            fixture.app.command("/hi-socket-mode", (request, context) -> {
                handledRequest.set(request);
                return context.ack();
            });
            fixture.socketApp.startAsync();
            fixture.enqueueCommand("legacy-delivery");
            Request<?> request = observed.poll(5, TimeUnit.SECONDS);
            assertNotNull(request);
            assertEquals("legacy-delivery", request.getSocketModeEnvelopeId());
            assertNull(request.getSocketModeDispatchDelayNanos());
            assertEquals(new AckResponse("legacy-delivery"), fixture.acknowledgements.poll(5, TimeUnit.SECONDS));
            assertSame(request, handledRequest.get());
        }
    }

    @Test
    public void middlewareFailureUsesTheConfiguredErrorHandler() throws Exception {
        AtomicReference<SocketModeApp.ErrorContext> error = new AtomicReference<>();
        try (Fixture fixture = new Fixture(new TimedMessageQueue(), context -> {
            error.set(context);
            return Response.ok();
        })) {
            AtomicReference<Request<?>> observed = new AtomicReference<>();
            AtomicBoolean handled = new AtomicBoolean();
            fixture.app.use((request, response, chain) -> {
                observed.set(request);
                throw new IllegalStateException("Middleware failed");
            });
            fixture.app.command("/hi-socket-mode", (request, context) -> {
                handled.set(true);
                return context.ack();
            });
            fixture.socketApp.startAsync();
            fixture.enqueueCommand("failed-delivery");

            assertEquals(new AckResponse("failed-delivery"), fixture.acknowledgements.poll(5, TimeUnit.SECONDS));
            assertEquals("failed-delivery", observed.get().getSocketModeEnvelopeId());
            assertNotNull(observed.get().getSocketModeDispatchDelayNanos());
            assertSame(observed.get(), error.get().getRequest());
            assertEquals("Middleware failed", error.get().getException().getMessage());
            assertFalse(handled.get());
        }
    }

    private static class Fixture implements AutoCloseable {
        // Real receive queue, dispatcher, Bolt parser, middleware and handler executor.
        // Only network connection/send are stubbed.
        private final Slack slack = spy(Slack.getInstance(new SlackConfig()));
        private final SocketModeClient socket;
        private final App app = new App(AppConfig.builder().slack(slack).build(), new ArrayList<>());
        private final SocketModeApp socketApp;
        private final BlockingQueue<AckResponse> acknowledgements = new LinkedBlockingQueue<>();

        Fixture(SocketModeMessageQueue queue) throws Exception {
            this(queue, null);
        }

        Fixture(SocketModeMessageQueue queue, Function<SocketModeApp.ErrorContext, Response> errorHandler) throws Exception {
            socket = spy(new SocketModeClientJavaWSImpl(
                    slack, "xapp-test", "ws://127.0.0.1:1", 1, queue, false, false, 5000));
            doNothing().when(socket).connectToNewEndpoint();
            doReturn(socket).when(slack).socketMode(anyString(), any(SocketModeClient.Backend.class));
            doAnswer(invocation -> {
                String response = invocation.getArgument(0);
                acknowledgements.add(GSON.fromJson(response, AckResponse.class));
                return null;
            }).when(socket).sendSocketModeResponse(anyString());
            socketApp = errorHandler == null
                    ? new SocketModeApp("xapp-test", app, SocketModeClient.Backend.JavaWebSocket, 1)
                    : new SocketModeApp("xapp-test", SocketModeClient.Backend.JavaWebSocket, errorHandler, app, 1);
        }

        void enqueueCommand(String envelopeId) {
            SlashCommandsEnvelope envelope = GSON.fromJson(MockSocketMode.COMMAND_ENVELOPE, SlashCommandsEnvelope.class);
            envelope.setEnvelopeId(envelopeId);
            socket.enqueueMessage(GSON.toJson(envelope));
        }

        @Override
        public void close() throws Exception {
            socketApp.close();
            socket.close();
            slack.close();
        }
    }

    private static void await(CountDownLatch latch) {
        try {
            assertTrue(latch.await(5, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}
