package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class UsergroupsTest {

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
    public void test() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).usergroupsCreate(r -> r.name("foo").description("desc"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usergroupsDisable(r -> r.usergroup("xxx"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usergroupsEnable(r -> r.usergroup("xxx"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usergroupsList(r -> r.includeCount(true))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usergroupsUpdate(r -> r.includeCount(true))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usergroupsUsersList(r -> r.includeDisabled(true))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usergroupsUsersUpdate(r -> r.usergroup("xxx"))
                .get().isOk(), is(true));
    }

}
