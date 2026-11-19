package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.MethodsCompletionException;
import com.slack.api.methods.MethodsConfig;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.impl.AsyncRateLimitExecutor;
import com.slack.api.methods.impl.AsyncRateLimitQueue;
import com.slack.api.methods.impl.TeamIdCache;
import com.slack.api.methods.response.users.UsersListResponse;
import com.slack.api.util.thread.DaemonThreadExecutorServiceProvider;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class AsyncRateLimitRejectTest {

    private final String executorName = getClass().getName() + "_" + UUID.randomUUID();
    private final String token = "xoxb-test-" + UUID.randomUUID();
    private final AtomicInteger usersListCalls = new AtomicInteger();
    private Server server;
    private SlackConfig config;
    private Slack slack;
    private int firstStatus = 429;
    private String retryAfter = "0";
    private String firstBody = "{\"ok\":false,\"error\":\"ratelimited\"}";

    @Before
    public void setup() throws Exception {
        server = new Server(0);
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(new ServletHolder(new HttpServlet() {
            @Override
            protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
                response.setContentType("application/json");
                if (request.getRequestURI().equals("/api/auth.test")) {
                    response.getWriter().write("{\"ok\":true,\"team_id\":\"T_RATE_LIMIT_TEST\"}");
                } else if (request.getRequestURI().equals("/api/users.list")) {
                    if (usersListCalls.incrementAndGet() == 1) {
                        response.setStatus(firstStatus);
                        response.setHeader("Retry-After", retryAfter);
                        response.getWriter().write(firstBody);
                    } else {
                        response.getWriter().write("{\"ok\":true,\"members\":[]}");
                    }
                } else {
                    response.setStatus(404);
                }
            }
        }), "/*");
        server.setHandler(handler);
        server.start();

        config = new SlackConfig();
        // A private provider prevents close() from shutting down other tests' thread pools.
        config.setExecutorServiceProvider(new DaemonThreadExecutorServiceProvider());
        config.getMethodsConfig().setExecutorName(executorName);
        config.getMethodsConfig().setMaxIdleMills(1000);
        config.setMethodsEndpointUrlPrefix("http://127.0.0.1:"
                + ((ServerConnector) server.getConnectors()[0]).getLocalPort() + "/api/");
        slack = Slack.getInstance(config);
    }

    @After
    public void tearDown() throws Exception {
        try {
            if (slack != null) {
                slack.close();
                config.getSCIM2Config().getMetricsDatastore().close();
            }
        } finally {
            try {
                if (server != null) {
                    server.stop();
                }
            } finally {
                // These caches have no public removal API. Remove only this test's entries.
                removeCacheEntry(AsyncRateLimitExecutor.class, "ALL_EXECUTORS", executorName);
                removeCacheEntry(AsyncRateLimitQueue.class, "ALL_QUEUES", executorName);
                removeCacheEntry(TeamIdCache.class, "TOKEN_TO_TEAM_ID", token);
            }
        }
    }

    private static void removeCacheEntry(Class<?> type, String name, String key) throws Exception {
        Field field = type.getDeclaredField(name);
        field.setAccessible(true);
        ((Map<?, ?>) field.get(null)).remove(key);
    }

    @Test
    public void enabledRejects429WithRetryAfterWithoutRetrying() throws Exception {
        config.getMethodsConfig().setRejectRateLimitedCalls(true);
        // A retry would wait longer than the future's deadline; rejection must not wait for it.
        retryAfter = "30";
        SlackApiException exception = expectHttpFailure();
        assertEquals(429, exception.getResponse().code());
        assertEquals("30", exception.getResponse().header("Retry-After"));
        assertEquals(1, usersListCalls.get());
    }

    @Test
    public void callbackCanInspectOriginal429Cause() throws Exception {
        config.getMethodsConfig().setRejectRateLimitedCalls(true);
        retryAfter = "30";
        CompletableFuture<SlackApiException> observed = new CompletableFuture<>();
        slack.methodsAsync(token).usersList(r -> r).whenComplete((response, failure) -> {
            Throwable cause = failure instanceof CompletionException ? failure.getCause() : failure;
            if (cause instanceof MethodsCompletionException) {
                observed.complete(((MethodsCompletionException) cause).getSlackApiException());
            } else {
                observed.completeExceptionally(new AssertionError("Expected the HTTP 429 cause", failure));
            }
        });
        SlackApiException exception = observed.get(2, TimeUnit.SECONDS);
        assertEquals(429, exception.getResponse().code());
        assertEquals("30", exception.getResponse().header("Retry-After"));
        assertEquals(1, usersListCalls.get());
    }

    @Test
    public void originalConstructorPreservesAutomaticRetry() throws Exception {
        MethodsConfig previous = config.getMethodsConfig();
        MethodsConfig legacy = new MethodsConfig(previous.isStatsEnabled(), previous.getExecutorName(),
                previous.getMaxIdleMills(), previous.getDefaultThreadPoolSize(),
                previous.getExecutorServiceProvider(), previous.getCustomThreadPoolSizes(),
                previous.getMetricsDatastore(), previous.getCustomRateLimitResolver());
        config.setMethodsConfig(legacy);
        assertRetrySucceeds();
    }


    @Test
    public void defaultRetries429AndReturnsSuccess() throws Exception {
        assertRetrySucceeds();
    }

    @Test
    public void explicitlyDisabledRetries429AndReturnsSuccess() throws Exception {
        config.getMethodsConfig().setRejectRateLimitedCalls(false);
        assertRetrySucceeds();
    }

    private void assertRetrySucceeds() throws Exception {
        // Retry-After: 0 exercises the real retry path without a multi-second sleep.
        UsersListResponse response = slack.methodsAsync(token).usersList(r -> r).get(2, TimeUnit.SECONDS);
        assertTrue(response.isOk());
        assertEquals(2, usersListCalls.get());
    }

    @Test
    public void enabledPreservesOtherHttpFailures() throws Exception {
        config.getMethodsConfig().setRejectRateLimitedCalls(true);
        assertOtherHttpFailure();
    }

    @Test
    public void disabledPreservesOtherHttpFailures() throws Exception {
        config.getMethodsConfig().setRejectRateLimitedCalls(false);
        assertOtherHttpFailure();
    }

    private void assertOtherHttpFailure() throws Exception {
        firstStatus = 400;
        firstBody = "{\"ok\":false,\"error\":\"invalid_request\"}";
        assertEquals(400, expectHttpFailure().getResponse().code());
        assertEquals(1, usersListCalls.get());
    }

    @Test
    public void enabledPreservesSlackApiErrorResponses() throws Exception {
        config.getMethodsConfig().setRejectRateLimitedCalls(true);
        assertSlackApiErrorResponse();
    }

    @Test
    public void disabledPreservesSlackApiErrorResponses() throws Exception {
        config.getMethodsConfig().setRejectRateLimitedCalls(false);
        assertSlackApiErrorResponse();
    }

    private void assertSlackApiErrorResponse() throws Exception {
        firstStatus = 200;
        firstBody = "{\"ok\":false,\"error\":\"missing_scope\"}";
        UsersListResponse response = slack.methodsAsync(token).usersList(r -> r).get(2, TimeUnit.SECONDS);
        assertFalse(response.isOk());
        assertEquals("missing_scope", response.getError());
        assertEquals(1, usersListCalls.get());
    }

    private SlackApiException expectHttpFailure() throws Exception {
        try {
            slack.methodsAsync(token).usersList(r -> r).get(2, TimeUnit.SECONDS);
            fail("Expected the future to fail with a SlackApiException");
            return null;
        } catch (ExecutionException e) {
            assertTrue(e.getCause() instanceof MethodsCompletionException);
            SlackApiException exception = ((MethodsCompletionException) e.getCause()).getSlackApiException();
            assertNotNull(exception);
            return exception;
        }
    }
}
