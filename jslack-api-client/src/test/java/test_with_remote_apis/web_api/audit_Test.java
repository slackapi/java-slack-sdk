package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.audit.response.ActionsResponse;
import com.github.seratch.jslack.api.audit.response.LogsResponse;
import com.github.seratch.jslack.api.audit.response.SchemasResponse;
import com.github.seratch.jslack.api.methods.SlackApiException;
import config.Constants;
import config.SlackTestConfig;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

// required scope - auditlogs:read
public class audit_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_ADMIN_OAUTH_ACCESS_TOKEN);

    @Test
    public void getSchemas() throws IOException, SlackApiException {
        if (token != null) {
            {
                SchemasResponse response = slack.audit(token).getSchemas();
                assertThat(response, is(notNullValue()));
            }
            {
                SchemasResponse response = slack.audit(token).getSchemas(req -> req);
                assertThat(response, is(notNullValue()));
            }
        }
    }

    @Test
    public void getActions() throws IOException, SlackApiException {
        if (token != null) {
            {
                ActionsResponse response = slack.audit(token).getActions();
                assertThat(response, is(notNullValue()));
            }
            {
                ActionsResponse response = slack.audit(token).getActions(req -> req);
                assertThat(response, is(notNullValue()));
            }
        }
    }

    @Test
    public void getLogs() throws IOException, SlackApiException {
        if (token != null) {
            LogsResponse response = slack.audit(token).getLogs(req -> req.oldest(1521214343).action("user_login").limit(10));
            assertThat(response, is(notNullValue()));
        }
    }

}
