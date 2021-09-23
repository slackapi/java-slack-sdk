package test_with_remote_apis;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.SlackApiTextResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.Data;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserDefinedMethodsTest {

    @Data
    public static class MyResponse implements SlackApiTextResponse {
        private boolean ok;
        private String warning;
        private String error;
        private String needed;
        private String provided;
        private transient Map<String, List<String>> httpResponseHeaders;

        private String url;
        private String team;
        private String user;
        private String teamId;
        private String userId;
        private String botId;
        private Boolean isEnterpriseInstall;
    }

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    @Test
    public void runMethods() throws IOException, SlackApiException {
        MyResponse response = slack.methods().postFormWithTokenAndParseResponse(
                f -> f.add("something", "important"), // request body
                "auth.test", // endpoint
                botToken, // xoxb-***
                MyResponse.class // response class
        );
        assertThat(response.getError(), is(nullValue()));
    }
}
