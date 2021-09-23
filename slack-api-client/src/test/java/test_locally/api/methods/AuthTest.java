package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static util.MockSlackApi.ValidToken;

public class AuthTest {

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
    public void headers() throws Exception {
        Map<String, List<String>> headers =
                slack.methods(ValidToken).authTest(r -> r).getHttpResponseHeaders();
        assertThat(headers, is(notNullValue()));
        assertThat(headers.size(), is(greaterThan(0)));
    }

    @Test
    public void headers_async() throws Exception {
        Map<String, List<String>> headers =
                slack.methodsAsync(ValidToken).authTest(r -> r).get().getHttpResponseHeaders();
        assertThat(headers, is(notNullValue()));
        assertThat(headers.size(), is(greaterThan(0)));
    }

    @Test
    public void auth() throws Exception {
        assertThat(slack.methods(ValidToken).authRevoke(r -> r.test(true)).isOk(), is(true));
        assertThat(slack.methods(ValidToken).authTest(r -> r).getTeamId(), is("T1234567"));
    }

    @Test
    public void auth_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).authRevoke(r -> r.test(true)).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).authTest(r -> r).get().getTeamId(), is("T1234567"));
    }

    @Test
    public void authTeamsList() throws Exception {
        assertThat(slack.methods(ValidToken).authTeamsList(r -> r.limit(10)).isOk(), is(true));
    }

}
