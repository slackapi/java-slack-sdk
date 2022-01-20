package test_locally.api.audit;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.audit.AuditClient;
import com.slack.api.audit.AuditConfig;
import com.slack.api.audit.response.ActionsResponse;
import com.slack.api.audit.response.LogsResponse;
import com.slack.api.methods.response.api.ApiTestResponse;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.rate_limits.metrics.RequestStats;
import com.slack.api.util.thread.DaemonThreadExecutorServiceProvider;
import com.slack.api.util.thread.ExecutorServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.FileReader;
import util.PortProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class ApiTest {

    public static final String ValidToken = "xoxb-this-is-valid-audit-logs";

    private static final FileReader reader = new FileReader("../json-logs/samples/audit/v1/");

    @WebServlet
    public static class AuditLogsMockApi extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String endpoint = req.getRequestURI().replaceFirst("^/api/", "");
            if (endpoint.equals("auth.test")) {
                String body = "{\n" +
                        "  \"ok\": true,\n" +
                        "  \"url\": \"https://java-slack-sdk-test.slack.com/\",\n" +
                        "  \"team\": \"java-slack-sdk-test\",\n" +
                        "  \"user\": \"test_user\",\n" +
                        "  \"team_id\": \"E12345678\",\n" +
                        "  \"enterprise_id\": \"E12345678\",\n" +
                        "  \"is_enterprise_install\": true,\n" +
                        "  \"user_id\": \"U1234567\"\n" +
                        "}";
                resp.setStatus(200);
                resp.getWriter().write(body);
                resp.setContentType("application/json");
                return;
            }
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            try (InputStream is = req.getInputStream();
                 InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader br = new BufferedReader(isr)) {
                String requestBody = br.lines().collect(Collectors.joining());
                log.info("request body: {}", requestBody);
            }
            String endpoint = req.getRequestURI().replaceFirst("^/api/", "");
            String body = reader.readWholeAsString(endpoint + ".json");
            body = body.replaceFirst("\"ok\": false,", "\"ok\": true,");
            if (body == null || body.trim().isEmpty()) {
                resp.setStatus(400);
                return;
            }
            resp.setStatus(200);
            resp.getWriter().write(body);
            resp.setContentType("application/json");
        }
    }

    int port = PortProvider.getPort(ApiTest.class.getName());
    Server server = new Server(port);

    {
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(AuditLogsMockApi.class, "/*");
    }

    @Before
    public void setup() throws Exception {
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void getLogs() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix("http://127.0.0.1:" + port + "/api/");
        config.setAuditEndpointUrlPrefix("http://127.0.0.1:" + port + "/api/");

        config.setAuditConfig(new AuditConfig());
        config.getAuditConfig().setExecutorName("getLogs" + System.currentTimeMillis());
        MetricsDatastore datastore = config.getAuditConfig().getMetricsDatastore();
        RequestStats stats = datastore.getStats(config.getAuditConfig().getExecutorName(), "E12345678");
        assertThat(stats.getAllCompletedCalls().get("logs"), is(nullValue()));

        AuditClient audit = Slack.getInstance(config).audit(ValidToken);
        LogsResponse response = audit.getLogs(r -> r.action("something"));
        assertThat(response.isOk(), is(true));

        stats = datastore.getStats(config.getAuditConfig().getExecutorName(), "E12345678");
        assertThat(stats.getAllCompletedCalls().get("logs"), is(1L));
    }

    @Test
    public void getSchemas() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix("http://127.0.0.1:" + port + "/api/");
        config.setAuditEndpointUrlPrefix("http://127.0.0.1:" + port + "/api/");

        config.setAuditConfig(new AuditConfig());
        config.getAuditConfig().setExecutorName("getSchemas" + System.currentTimeMillis());
        MetricsDatastore datastore = config.getAuditConfig().getMetricsDatastore();
        RequestStats stats = datastore.getStats(config.getAuditConfig().getExecutorName(), "E12345678");
        assertThat(stats.getAllCompletedCalls().get("schemas"), is(nullValue()));

        AuditClient audit = Slack.getInstance(config).audit(ValidToken);
        assertThat(audit.getSchemas().isOk(), is(true));

        stats = datastore.getStats(config.getAuditConfig().getExecutorName(), "E12345678");
        assertThat(stats.getAllCompletedCalls().get("schemas"), is(1L));
    }

    @Test
    public void getActions() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix("http://127.0.0.1:" + port + "/api/");
        config.setAuditEndpointUrlPrefix("http://127.0.0.1:" + port + "/api/");

        config.setAuditConfig(new AuditConfig());
        config.getAuditConfig().setExecutorName("getActions" + System.currentTimeMillis());
        MetricsDatastore datastore = config.getAuditConfig().getMetricsDatastore();
        RequestStats stats = datastore.getStats(config.getAuditConfig().getExecutorName(), "E12345678");
        assertThat(stats.getAllCompletedCalls().get("actions"), is(nullValue()));

        AuditClient audit = Slack.getInstance(config).audit(ValidToken);
        assertThat(audit.getActions().isOk(), is(true));

        stats = datastore.getStats(config.getAuditConfig().getExecutorName(), "E12345678");
        assertThat(stats.getAllCompletedCalls().get("actions"), is(1L));
    }

    @Test
    public void customizeExecutorService() throws Exception {
        final AtomicBoolean executorCreationCalled = new AtomicBoolean(false);
        final AtomicBoolean schedulerCreationCalled = new AtomicBoolean(false);
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix("http://127.0.0.1:" + port + "/api/");
        config.setAuditEndpointUrlPrefix("http://127.0.0.1:" + port + "/api/");
        config.setExecutorServiceProvider(new ExecutorServiceProvider() {
            @Override
            public ExecutorService createThreadPoolExecutor(String threadGroupName, int poolSize) {
                executorCreationCalled.set(true);
                return DaemonThreadExecutorServiceProvider.getInstance()
                        .createThreadPoolExecutor(threadGroupName, poolSize);
            }

            @Override
            public ScheduledExecutorService createThreadScheduledExecutor(String threadGroupName) {
                schedulerCreationCalled.set(true);
                return DaemonThreadExecutorServiceProvider.getInstance()
                        .createThreadScheduledExecutor(threadGroupName);
            }
        });
        Slack slack = Slack.getInstance(config);
        {
            ActionsResponse response = slack.audit(ValidToken).getActions();
            assertThat(response.getError(), is(""));
            // the sync client does not use ExecutorService under the hood
            assertThat(executorCreationCalled.get(), is(false));
            assertThat(schedulerCreationCalled.get(), is(true));
        }
        {
            ActionsResponse response = slack.auditAsync(ValidToken).getActions().get();
            assertThat(response.getError(), is(""));
            assertThat(executorCreationCalled.get(), is(true));
        }

    }

}
