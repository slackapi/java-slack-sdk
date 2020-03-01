package test_locally;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.audit.AuditClient;
import com.slack.api.methods.MethodsStats;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.api.ApiTestResponse;
import com.slack.api.rtm.RTMClient;
import com.slack.api.scim.SCIMClient;
import com.slack.api.util.http.SlackHttpClient;
import com.slack.api.webhook.WebhookResponse;
import org.junit.Test;
import util.MockSlackApi;
import util.MockSlackApiServer;
import util.MockWebhookServer;

import java.io.IOException;

import static com.slack.api.webhook.WebhookPayloads.payload;
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
            MethodsStats stats = Slack.getInstance().methodsStats("T1234567");
            assertNotNull(stats);
        }
        {
            MethodsStats stats = Slack.getInstance().methodsStats("executor", "T1234567");
            assertNotNull(stats);
        }
    }

}
