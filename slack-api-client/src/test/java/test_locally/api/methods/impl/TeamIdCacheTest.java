package test_locally.api.methods.impl;

import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.impl.TeamIdCache;
import com.slack.api.util.http.SlackHttpClient;
import org.junit.Test;

import static org.junit.Assert.*;

public class TeamIdCacheTest {

    // https://github.com/slackapi/java-slack-sdk/issues/566
    @Test
    public void shouldNotRevealAuthorizationHeaderEvenIfItIsInvalid() {
        String token = "xoxb-111-222-zzz";
        try {
            TeamIdCache teamIdCache = new TeamIdCache(new MethodsClientImpl(new SlackHttpClient()));
            teamIdCache.lookupOrResolve("xoxb-111-222-zzz\nsomething wrong");
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            assertFalse(e.getMessage(), e.getMessage().contains(token));
        }
    }
}
