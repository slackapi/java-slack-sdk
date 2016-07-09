package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.oauth.OAuthAccessRequest;
import com.github.seratch.jslack.api.methods.response.oauth.OAuthAccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class Slack_oauth_Test {

    Slack slack = Slack.getInstance();

    @Test
    public void test() throws IOException, SlackApiException {
        {
            OAuthAccessResponse response = slack.methods().oauthAccess(OAuthAccessRequest.builder()
                    .clientId("3485157640.XXXX")
                    .clientSecret("XXXXX")
                    .code("")
                    .redirectUri("http://seratch.net/foo")
                    .build());
            assertThat(response.isOk(), is(false));
        }
    }

}