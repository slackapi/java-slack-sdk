package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.groups.GroupsCreateResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class groups_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Ignore
    @Test
    public void groupsCreate() throws IOException, SlackApiException {
        String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

        String name = "secret-" + System.currentTimeMillis();
        GroupsCreateResponse creationResponse = slack.methods().groupsCreate(r -> r.token(userToken).name(name));
        assertThat(creationResponse.getError(), is("method_deprecated"));
    }

}
