package test_with_remote_apis;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.SlackApiResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.Data;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserDefinedMethodsTest {

    @Data
    public static class MyResponse implements SlackApiResponse {
        private boolean ok;
        private String warning;
        private String error;
        private String needed;
        private String provided;

        private String url;
        private String team;
        private String user;
        private String teamId;
        private String userId;
    }

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void runMethods() throws IOException, SlackApiException {
        MyResponse response = slack.methods().postFormWithTokenAndParseResponse(
                f -> f.add("something", "important"), // request body
                "auth.test", // endpoint
                token, // xoxb-***
                MyResponse.class // response class
        );
        assertThat(response.getError(), is(nullValue()));
    }
}
