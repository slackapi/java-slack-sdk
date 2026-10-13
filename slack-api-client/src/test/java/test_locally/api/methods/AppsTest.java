package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.metrics.MemoryMetricsDatastore;
import com.slack.api.methods.response.apps.user.connection.AppsUserConnectionBulkUpdateResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static test_locally.api.status.ApiTest.ValidToken;

public class AppsTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void appsUserConnectionUpdate() throws Exception {
        assertThat(slack.methods(ValidToken).appsUserConnectionUpdate(r -> r
                .userId("U12345678").status("connected")).isOk(), is(true));
    }

    @Test
    public void appsUserConnectionUpdate_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).appsUserConnectionUpdate(r -> r
                .userId("U12345678").status("connected")).get().isOk(), is(true));
    }

    @Test
    public void appsUserConnectionBulkUpdate() throws Exception {
        AppsUserConnectionBulkUpdateResponse response = slack.methods(ValidToken).appsUserConnectionBulkUpdate(r -> r
                .connectedUserIds(Arrays.asList("U12345678"))
                .disconnectedUserIds(Arrays.asList("U87654321")));
        assertThat(response.isOk(), is(true));
        assertThat(response.getChangedUserIds(), is(Arrays.asList("U00000000")));
        assertThat(response.getFailures().get(0).getUserId(), is("U00000000"));
    }

    @Test
    public void appsUserConnectionBulkUpdate_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).appsUserConnectionBulkUpdate(r -> r
                .connectedUserIds(Arrays.asList("U12345678"))).get().isOk(), is(true));
    }

    @Test
    public void appsUninstall_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).appsUninstall(r -> r.clientId("x").clientSecret("y"))
                .get().isOk(), is(true));
    }

    @Test
    public void appsManifest_async() throws Exception {
        slack.getConfig().getMethodsConfig().setMetricsDatastore(new MemoryMetricsDatastore(1));
        String token = ValidToken + "-2222";
        assertThat(slack.methodsAsync(token).appsManifestCreate(r -> r).get().isOk(), is(true));
        assertThat(slack.methodsAsync(token).appsManifestDelete(r -> r).get().isOk(), is(true));
        assertThat(slack.methodsAsync(token).appsManifestExport(r -> r).get().isOk(), is(true));
        assertThat(slack.methodsAsync(token).appsManifestValidate(r -> r).get().isOk(), is(true));
        assertThat(slack.methodsAsync(token).appsManifestUpdate(r -> r).get().isOk(), is(true));
        // TODO: add tests that pass for tooling.tokens.rotate API
        // assertThat(slack.methodsAsync().toolingTokensRotate(r -> r).get().isOk(), is(true));
    }

}
