package test_locally;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.audit.AuditClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.impl.ThreadPools;
import com.slack.api.methods.response.api.ApiTestResponse;
import com.slack.api.rtm.RTMClient;
import com.slack.api.scim.SCIMClient;
import com.slack.api.util.http.SlackHttpClient;
import com.slack.api.rate_limits.metrics.RequestStats;
import com.slack.api.webhook.WebhookResponse;
import org.junit.Test;
import util.MockSlackApi;
import util.MockSlackApiServer;
import util.MockWebhookServer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import static com.slack.api.webhook.WebhookPayloads.payload;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class SlackTest {

    @Test
    public void noArgConstructor() throws IOException, SlackApiException {
        Slack slack = new Slack();
        assertNotNull(slack);

        ApiTestResponse response = slack.methods().apiTest(r -> r.foo("bar"));
        assertNull(response.getError());
    }

    @Test
    public void getInstance_config_httpClient() throws IOException, SlackApiException {
        SlackConfig config = new SlackConfig();
        Slack slack = Slack.getInstance(config, new SlackHttpClient());
        assertNotNull(slack);
        assertEquals(config, slack.getConfig());

        ApiTestResponse response = slack.methods().apiTest(r -> r.foo("bar"));
        assertNull(response.getError());
    }

    @Test
    public void getInstance_httpClient() throws IOException, SlackApiException {
        Slack slack = Slack.getInstance(new SlackHttpClient());
        assertNotNull(slack);

        ApiTestResponse response = slack.methods().apiTest(r -> r.foo("bar"));
        assertNull(response.getError());
    }

    @Test
    public void incomingWebhooks_text() throws Exception {
        MockWebhookServer webhookServer = new MockWebhookServer();
        webhookServer.start();
        try {
            Slack slack = Slack.getInstance();
            WebhookResponse response = slack.send(webhookServer.getWebhookUrl(), "{\"text\":\":wave: Hello!\"");
            assertEquals(200L, response.getCode().longValue());
            assertEquals("OK", response.getBody());
        } finally {
            webhookServer.stop();
        }
    }

    @Test
    public void incomingWebhooks_payload() throws Exception {
        MockWebhookServer webhookServer = new MockWebhookServer();
        webhookServer.start();
        try {
            Slack slack = Slack.getInstance();
            WebhookResponse response = slack.send(webhookServer.getWebhookUrl(), payload(r -> r.text(":wave: Hello!")));
            assertEquals(200L, response.getCode().longValue());
            assertEquals("OK", response.getBody());
        } finally {
            webhookServer.stop();
        }
    }

    @Test
    public void rtm() throws Exception {
        MockSlackApiServer apiServer = new MockSlackApiServer();
        apiServer.start();

        try {
            SlackConfig config = new SlackConfig();
            config.setMethodsEndpointUrlPrefix(apiServer.getMethodsEndpointPrefix());
            Slack slack = Slack.getInstance(config);
            RTMClient client = slack.rtm(MockSlackApi.ValidToken);
            assertNotNull(client); // cannot connect, tho
        } finally {
            apiServer.stop();
        }
    }

    @Test
    public void rtmStart() throws Exception {
        MockSlackApiServer apiServer = new MockSlackApiServer();
        apiServer.start();

        try {
            SlackConfig config = new SlackConfig();
            config.setMethodsEndpointUrlPrefix(apiServer.getMethodsEndpointPrefix());
            Slack slack = Slack.getInstance(config);
            RTMClient client = slack.rtmStart(MockSlackApi.ValidToken);
            assertNotNull(client); // cannot connect, tho
        } finally {
            apiServer.stop();
        }
    }

    @Test
    public void audit() {
        AuditClient client = Slack.getInstance().audit();
        assertNotNull(client);
    }

    @Test
    public void scim() {
        SCIMClient client = Slack.getInstance().scim();
        assertNotNull(client);
        assertEquals(SlackConfig.DEFAULT.getScimEndpointUrlPrefix(), client.getEndpointUrlPrefix());
    }

    @Test
    public void methodsStats() {
        {
            RequestStats stats = Slack.getInstance().methodsStats("T1234567");
            assertNotNull(stats);
        }
        {
            RequestStats stats = Slack.getInstance().methodsStats("executor", "T1234567");
            assertNotNull(stats);
        }
    }

    @Test
    public void auditStats() {
        {
            RequestStats stats = Slack.getInstance().auditStats("E1234567");
            assertNotNull(stats);
        }
        {
            RequestStats stats = Slack.getInstance().auditStats("executor", "T1234567");
            assertNotNull(stats);
        }
    }

    @Test
    public void scimStats() {
        {
            RequestStats stats = Slack.getInstance().scimStats("E1234567");
            assertNotNull(stats);
        }
        {
            RequestStats stats = Slack.getInstance().scimStats("executor", "E1234567");
            assertNotNull(stats);
        }
    }

    @Test
    public void closeMethod() throws Exception {
        SlackConfig config = new SlackConfig();
        Slack slack = Slack.getInstance(config);
        ExecutorService methods = ThreadPools.getDefault(config.getMethodsConfig());
        ExecutorService scim = com.slack.api.scim.impl.ThreadPools.getDefault(config.getSCIMConfig());
        ExecutorService audit = com.slack.api.audit.impl.ThreadPools.getDefault(config.getAuditConfig());
        assertThat(methods.isTerminated(), is(false));
        assertThat(scim.isTerminated(), is(false));
        assertThat(audit.isTerminated(), is(false));

        slack.close();

        assertThat(methods.isTerminated(), is(true));
        assertThat(scim.isTerminated(), is(true));
        assertThat(audit.isTerminated(), is(true));
    }

}
