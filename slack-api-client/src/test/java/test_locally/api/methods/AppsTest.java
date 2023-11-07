package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.metrics.MemoryMetricsDatastore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

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
        assertThat(slack.methodsAsync(token).toolingTokensRotate(r -> r).get().isOk(), is(true));
    }

}
