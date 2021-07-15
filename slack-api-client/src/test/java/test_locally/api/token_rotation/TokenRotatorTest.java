package test_locally.api.token_rotation;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.token_rotation.RefreshedToken;
import com.slack.api.token_rotation.TokenRotator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;

public class TokenRotatorTest {

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
    public void refresh() throws Exception {
        TokenRotator tokenRotator = new TokenRotator(
                slack.methods(),
                1000 * 60 * 60 * 24 * 365,
                "111.222",
                "secret"
        );
        Optional<RefreshedToken> refreshedToken = tokenRotator.performTokenRotation(r -> r
                .accessToken("xoxe.xoxb-1-initial")
                .refreshToken("xoxe-1-xxx")
                .expiresAt(System.currentTimeMillis() + 10_000L)
        );
        assertThat(refreshedToken.get().getRefreshToken(), is(notNullValue()));
    }

    @Test
    public void skipRefreshing() throws Exception {
        TokenRotator tokenRotator = new TokenRotator(
                slack.methods(),
                1_000,
                "111.222",
                "secret"
        );
        Optional<RefreshedToken> refreshedToken = tokenRotator.performTokenRotation(r -> r
                .accessToken("xoxe.xoxb-1-initial")
                .refreshToken("xoxe-1-xxx")
                .expiresAt(System.currentTimeMillis() + 10_000L)
        );
        assertFalse(refreshedToken.isPresent());
    }
}
