package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.response.users.UsersLookupByEmailResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class UsersTest {

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
        assertThat(slack.methods(ValidToken).usersConversations(r -> r.user("U123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).usersDeletePhoto(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).usersGetPresence(r -> r.user("U123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).usersIdentity(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).usersInfo(r -> r.user("U123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).usersList(r -> r.limit(1).cursor("xxx"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).usersLookupByEmail(r -> r.email("foo@example.com"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).usersSetActive(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).usersSetPhoto(r -> r.imageData("foo".getBytes()))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).usersSetPresence(r -> r.presence("presence"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).usersProfileGet(r -> r.user("U123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).usersProfileSet(r -> r.user("U123").name("name").value("value"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).usersDiscoverableContactsLookup(r -> r.email("foo@example.com"))
                .isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).usersConversations(r -> r.user("U123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usersDeletePhoto(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usersGetPresence(r -> r.user("U123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usersIdentity(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usersInfo(r -> r.user("U123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usersList(r -> r.limit(1).cursor("xxx"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usersLookupByEmail(r -> r.email("foo@example.com"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usersSetActive(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usersSetPhoto(r -> r.imageData("foo".getBytes()))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usersSetPresence(r -> r.presence("presence"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usersProfileGet(r -> r.user("U123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usersProfileSet(r -> r.user("U123").name("name").value("value"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).usersDiscoverableContactsLookup(r -> r.email("foo@example.com"))
                .get().isOk(), is(true));
    }

    // NOTE: we can safely remove this tests since v1.1
    @Test
    public void test_deprecated_UsersLookupByEmailResponse() throws Exception {
        UsersLookupByEmailResponse response = slack.methods(ValidToken).usersLookupByEmail(r -> r.email("foo@example.com"));
        assertThat(response.isOk(), is(true));
        // for backward-compatibility
        com.slack.api.methods.response.channels.UsersLookupByEmailResponse deprecatedResponse
                = slack.methods(ValidToken).usersLookupByEmail(r -> r.email("foo@example.com"));
        assertThat(deprecatedResponse.isOk(), is(true));
    }

}
