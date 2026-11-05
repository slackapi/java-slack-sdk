package test_locally;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.bolt.socket_mode.listener.SocketModeRequestListener;
import com.slack.api.socket_mode.SocketModeClient;
import com.slack.api.socket_mode.impl.SocketModeClientJavaWSImpl;
import com.slack.api.socket_mode.queue.SocketModeMessageQueue;
import com.slack.api.socket_mode.queue.impl.ConcurrentLinkedMessageQueue;
import com.slack.api.socket_mode.queue.impl.TimedMessageQueue;
import com.slack.api.socket_mode.request.SlashCommandsEnvelope;
import com.slack.api.socket_mode.response.AckResponse;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import util.socket_mode.MockSocketMode;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

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
            AtomicReference<SocketModeRequestListener.State> secondState = new AtomicReference<>();
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
            fixture.socketApp.addRequestListener(new SocketModeRequestListener() {
                @Override
                public void accept(State state) {
                    if ("queued-delivery".equals(state.getEnvelopeId())) {
                        secondState.set(state);
                    }
                }
            });
            when(fixture.app.run(any())).thenAnswer(invocation -> {
                if (handled.getAndIncrement() == 0) {
                    handlerHeld.countDown();
                    await(releaseHandler);
                } else {
                    secondStarted.set(System.nanoTime());
                    secondRequest.set(invocation.getArgument(0));
                    secondHandled.countDown();
                }
                return Response.ok();
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
                assertNull(secondState.get());
                assertNull(fixture.acknowledgements.poll());
                long handlerReleased = System.nanoTime();
                releaseHandler.countDown();
                assertTrue(secondHandled.await(5, TimeUnit.SECONDS));

                SocketModeRequestListener.State state = secondState.get();
                assertEquals("queued-delivery", state.getEnvelopeId());
                assertSame(secondRequest.get(), state.getRequest());
                assertNotNull(state.getDispatchDelayNanos());
                assertTrue(state.getDispatchDelayNanos() >= handlerReleased - afterEnqueue);
                assertTrue(state.getDispatchDelayNanos() <= secondStarted.get() - beforeEnqueue);
                assertEquals(new AckResponse("first-delivery"), fixture.acknowledgements.poll(5, TimeUnit.SECONDS));
                assertEquals(new AckResponse("queued-delivery"), fixture.acknowledgements.poll(5, TimeUnit.SECONDS));
            } finally {
                releaseReceiver.countDown();
                releaseHandler.countDown();
            }
        }
    }

    @Test
    public void unknownTimingAndListenerFailureDoNotPreventHandlingOrAcknowledgement() throws Exception {
        try (Fixture fixture = new Fixture(new ConcurrentLinkedMessageQueue())) {
            fixture.socketApp.addRequestListener(new SocketModeRequestListener() {
                @Override
                public void accept(State state) {
                    throw new IllegalStateException("Metrics listener unavailable");
                }
            });
            BlockingQueue<SocketModeRequestListener.State> observed = new LinkedBlockingQueue<>();
            fixture.socketApp.addRequestListener(new SocketModeRequestListener() {
                @Override
                public void accept(State state) {
                    observed.add(state);
                }
            });
            fixture.socketApp.startAsync();
            fixture.enqueueCommand("legacy-delivery");
            SocketModeRequestListener.State state = observed.poll(5, TimeUnit.SECONDS);
            assertNotNull(state);
            assertEquals("legacy-delivery", state.getEnvelopeId());
            assertNull(state.getDispatchDelayNanos());
            assertEquals(new AckResponse("legacy-delivery"), fixture.acknowledgements.poll(5, TimeUnit.SECONDS));
            ArgumentCaptor<Request> request = ArgumentCaptor.forClass(Request.class);
            verify(fixture.app).run(request.capture());
            assertSame(state.getRequest(), request.getValue());
        }
    }

    private static class Fixture implements AutoCloseable {
        // Real receive queue, dispatcher, Bolt parser and handler executor. Only network
        // connection/send and the application handler are stubbed.
        private final Slack slack = spy(Slack.getInstance(new SlackConfig()));
        private final SocketModeClient socket;
        private final App app = mock(App.class);
        private final SocketModeApp socketApp;
        private final BlockingQueue<AckResponse> acknowledgements = new LinkedBlockingQueue<>();

        Fixture(SocketModeMessageQueue queue) throws Exception {
            socket = spy(new SocketModeClientJavaWSImpl(
                    slack, "xapp-test", "ws://127.0.0.1:1", 1, queue, false, false, 5000));
            doNothing().when(socket).connectToNewEndpoint();
            doReturn(socket).when(slack).socketMode(anyString(), any(SocketModeClient.Backend.class));
            doAnswer(invocation -> {
                String response = invocation.getArgument(0);
                acknowledgements.add(GSON.fromJson(response, AckResponse.class));
                return null;
            }).when(socket).sendSocketModeResponse(anyString());
            when(app.slack()).thenReturn(slack);
            when(app.config()).thenReturn(AppConfig.builder().slack(slack).build());
            when(app.run(any())).thenReturn(Response.ok());
            socketApp = new SocketModeApp("xapp-test", app, SocketModeClient.Backend.JavaWebSocket, 1);
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
