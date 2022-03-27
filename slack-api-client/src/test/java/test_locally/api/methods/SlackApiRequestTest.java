package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.SlackApiRequest;
import com.slack.api.methods.request.auth.AuthTestRequest;
import com.slack.api.methods.response.auth.AuthTestResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ExpiredToken;
import static util.MockSlackApi.ValidToken;

public class SlackApiRequestTest {

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

    @Test(expected = UnsupportedOperationException.class)
    public void defaultSetToken() {
        SlackApiRequest req = new SlackApiRequest() {
            @Override
            public String getToken() {
                return null;
            }
        };
        req.setToken("foo");
    }

    @Test
    public void requestObjectCreation() {
        AuthTestRequest req = AuthTestRequest.builder().token("xoxb-old-token").build();
        assertThat(req.getToken(), is("xoxb-old-token"));
        req.setToken("xoxb-new-token"); // no exception here
        assertThat(req.getToken(), is("xoxb-new-token"));
    }

    @Test
    public void setTokenUseCase() throws Exception {
        AuthTestRequest req = AuthTestRequest.builder().token(ExpiredToken).build();
        AuthTestResponse response1 = slack.methods().authTest(req);
        assertThat(response1.getError(), is("token_expired"));

        replaceToken(req, ValidToken);
        AuthTestResponse response2 = slack.methods().authTest(req);
        assertThat(response2.getError(), is(nullValue()));
    }

    private static void replaceToken(SlackApiRequest req, String newToken) {
        req.setToken(newToken);
    }

}