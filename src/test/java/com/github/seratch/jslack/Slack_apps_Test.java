package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.apps.permissions.AppsPermissionsRequestRequest;
import com.github.seratch.jslack.api.methods.response.apps.permissions.AppsPermissionsRequestResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class Slack_apps_Test {

    Slack slack = Slack.getInstance();

    // TODO: valid test
    @Test
    public void appsPermissionsRequest() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        AppsPermissionsRequestResponse response = slack.methods().appsPermissionsRequest(AppsPermissionsRequestRequest.builder()
                .token(token)
                .triggerId("dummy")
                .build());
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is("not_allowed_token_type"));
    }

}