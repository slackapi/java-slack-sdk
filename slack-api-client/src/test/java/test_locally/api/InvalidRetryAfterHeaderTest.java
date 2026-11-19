package test_locally.api;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.audit.AuditApiException;
import com.slack.api.methods.MethodsCompletionException;
import com.slack.api.methods.SlackApiException;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.scim.SCIMApiException;
import com.slack.api.scim.SCIMEndpointName;
import com.slack.api.scim2.SCIM2ApiException;
import com.slack.api.scim2.SCIM2EndpointName;
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
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

/**
 * Tests behavior when a 429 response contains a non-numeric Retry-After header.
 */
public class InvalidRetryAfterHeaderTest {

    private static final String TEAM_ID = "T1234567";

    private final String executorName = getClass().getName() + "_" + UUID.randomUUID();
    private final String token = "xoxb-test-" + UUID.randomUUID();
    private volatile String retryAfter = "notanumber";
    private Server server;
    private SlackConfig config;
    private Slack slack;

    @Before
    public void setup() throws Exception {
        server = new Server(0);
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(new ServletHolder(new HttpServlet() {
            @Override
            protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
                response.setContentType("application/json");
                if (request.getRequestURI().equals("/api/auth.test")) {
                    response.getWriter().write("{\"ok\":true,\"team_id\":\"" + TEAM_ID + "\"}");
                    return;
                }
                response.setStatus(429);
                if (retryAfter != null) {
                    response.setHeader("Retry-After", retryAfter);
                }
                response.getWriter().write("{\"ok\":false,\"error\":\"ratelimited\","
                        + "\"Errors\":{\"description\":\"ratelimited\",\"code\":429}}");
            }
        }), "/*");
        server.setHandler(handler);
        server.start();

        String url = "http://127.0.0.1:" + ((ServerConnector) server.getConnectors()[0]).getLocalPort() + "/api/";
        config = new SlackConfig();
        // A private provider prevents close() from shutting down other tests' thread pools.
        config.setExecutorServiceProvider(new DaemonThreadExecutorServiceProvider());
        config.setMethodsEndpointUrlPrefix(url);
        config.setAuditEndpointUrlPrefix(url);
        config.setScimEndpointUrlPrefix(url);
        config.setScim2EndpointUrlPrefix(url);
        config.getMethodsConfig().setExecutorName(executorName);
        config.getAuditConfig().setExecutorName(executorName);
        config.getSCIMConfig().setExecutorName(executorName);
        config.getSCIM2Config().setExecutorName(executorName);
        config.synchronizeMetricsDatabases();
        slack = Slack.getInstance(config);
    }

    @After
    public void tearDown() throws Exception {
        try {
            slack.close();
        } finally {
            server.stop();
        }
    }

    @Test
    public void nonNumericRetryAfterHeaderShouldThrowSlackApiException() {
        SlackApiException e = assertThrows(SlackApiException.class,
                () -> slack.methods(token).usersList(r -> r));
        assertThat(e.getResponse().code(), is(429));
        assertThat(e.getResponse().header("Retry-After"), is("notanumber"));
        assertRetryTimeNotRecorded(config.getMethodsConfig().getMetricsDatastore(), "users.list");
    }

    @Test
    public void multipartRequest() {
        SlackApiException e = assertThrows(SlackApiException.class,
                () -> slack.methods(token).usersSetPhoto(r -> r.imageData(new byte[]{1})));
        assertThat(e.getResponse().code(), is(429));
        assertThat(e.getResponse().header("Retry-After"), is("notanumber"));
        assertRetryTimeNotRecorded(config.getMethodsConfig().getMetricsDatastore(), "users.setPhoto");
    }

    @Test
    public void asyncMethodsClientRejectingRateLimitedCalls() {
        config.getMethodsConfig().setRejectRateLimitedCalls(true);
        ExecutionException e = assertThrows(ExecutionException.class,
                () -> slack.methodsAsync(token).usersList(r -> r).get(10, TimeUnit.SECONDS));
        assertThat(e.getCause(), is(instanceOf(MethodsCompletionException.class)));
        SlackApiException cause = ((MethodsCompletionException) e.getCause()).getSlackApiException();
        assertThat(cause, is(notNullValue()));
        assertThat(cause.getResponse().code(), is(429));
        assertThat(cause.getResponse().header("Retry-After"), is("notanumber"));
    }

    @Test
    public void auditClient() {
        AuditApiException e = assertThrows(AuditApiException.class,
                () -> slack.audit(token).getLogs(r -> r));
        assertThat(e.getResponse().code(), is(429));
        assertThat(e.getResponse().header("Retry-After"), is("notanumber"));
        assertRetryTimeNotRecorded(config.getAuditConfig().getMetricsDatastore(), "logs");
    }

    @Test
    public void scimClient() {
        SCIMApiException e = assertThrows(SCIMApiException.class,
                () -> slack.scim(token).readUser(r -> r.id("W1234567")));
        assertThat(e.getResponse().code(), is(429));
        assertThat(e.getResponse().header("Retry-After"), is("notanumber"));
        assertRetryTimeNotRecorded(config.getSCIMConfig().getMetricsDatastore(), SCIMEndpointName.readUser.name());
    }

    @Test
    public void scim2Client() {
        SCIM2ApiException e = assertThrows(SCIM2ApiException.class,
                () -> slack.scim2(token).readUser(r -> r.id("W1234567")));
        assertThat(e.getResponse().code(), is(429));
        assertThat(e.getResponse().header("Retry-After"), is("notanumber"));
        assertRetryTimeNotRecorded(config.getSCIM2Config().getMetricsDatastore(), SCIM2EndpointName.readUser.name());
    }

    @Test
    public void numericRetryAfterHeaderIsStillRecorded() {
        retryAfter = "30";
        assertThrows(SlackApiException.class, () -> slack.methods(token).usersList(r -> r));
        assertThrows(SlackApiException.class, () -> slack.methods(token).usersSetPhoto(r -> r.imageData(new byte[]{1})));
        assertThrows(AuditApiException.class, () -> slack.audit(token).getLogs(r -> r));
        assertThrows(SCIMApiException.class, () -> slack.scim(token).readUser(r -> r.id("W1234567")));
        assertThrows(SCIM2ApiException.class, () -> slack.scim2(token).readUser(r -> r.id("W1234567")));

        assertRetryTimeRecorded(config.getMethodsConfig().getMetricsDatastore(), "users.list");
        assertRetryTimeRecorded(config.getMethodsConfig().getMetricsDatastore(), "users.setPhoto");
        assertRetryTimeRecorded(config.getAuditConfig().getMetricsDatastore(), "logs");
        assertRetryTimeRecorded(config.getSCIMConfig().getMetricsDatastore(), SCIMEndpointName.readUser.name());
        assertRetryTimeRecorded(config.getSCIM2Config().getMetricsDatastore(), SCIM2EndpointName.readUser.name());
    }

    @Test
    public void missingRetryAfterHeader() {
        retryAfter = null;
        SlackApiException e = assertThrows(SlackApiException.class,
                () -> slack.methods(token).usersList(r -> r));
        assertThat(e.getResponse().code(), is(429));
        assertRetryTimeNotRecorded(config.getMethodsConfig().getMetricsDatastore(), "users.list");
    }

    private void assertRetryTimeNotRecorded(MetricsDatastore datastore, String methodName) {
        assertThat(datastore.getRateLimitedMethodRetryEpochMillis(executorName, TEAM_ID, methodName), is(nullValue()));
    }

    private void assertRetryTimeRecorded(MetricsDatastore datastore, String methodName) {
        assertThat(datastore.getRateLimitedMethodRetryEpochMillis(executorName, TEAM_ID, methodName), is(notNullValue()));
    }
}
