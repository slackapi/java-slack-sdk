package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.audit.AuditApiException;
import com.github.seratch.jslack.api.audit.response.ActionsResponse;
import com.github.seratch.jslack.api.audit.response.LogsResponse;
import com.github.seratch.jslack.api.audit.response.SchemasResponse;
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
    public void getSchemas() throws IOException, AuditApiException {
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

    @Test(expected = IllegalStateException.class)
    public void getSchemas_missingToken() throws IOException, AuditApiException {
        SchemasResponse response = slack.audit().getSchemas();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void getSchemas_dummy() throws IOException, AuditApiException {
        SchemasResponse response = slack.audit("dummy").getSchemas();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void getActions() throws IOException, AuditApiException {
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

    @Test(expected = IllegalStateException.class)
    public void getActions_missingToken() throws IOException, AuditApiException {
        ActionsResponse response = slack.audit().getActions();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void getActions_dummy() throws IOException, AuditApiException {
        ActionsResponse response = slack.audit("dummy").getActions();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void getLogs() throws IOException, AuditApiException {
        if (token != null) {
            LogsResponse response = slack.audit(token).getLogs(req -> req.oldest(1521214343).action("user_login").limit(10));
            assertThat(response, is(notNullValue()));
        }
    }

    @Test(expected = IllegalStateException.class)
    public void getLogs_missingToken() throws IOException, AuditApiException {
        LogsResponse response = slack.audit().getLogs(req -> req.oldest(1521214343).action("user_login").limit(10));
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void getLogs_dummy() throws IOException {
        try {
            slack.audit("dummy").getLogs(req -> req.oldest(1521214343).action("user_login").limit(10));
            fail();
        } catch (AuditApiException e) {
            assertThat(e.getResponse().code(), is(401));
            assertThat(e.getError(), is(nullValue()));
        }
    }

}
