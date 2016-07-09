package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.api.ApiTestRequest;
import com.github.seratch.jslack.api.methods.response.api.ApiTestResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class Slack_api_test_Test {

    Slack slack = Slack.getInstance();

    @Test
    public void ok() throws IOException, SlackApiException {
        ApiTestResponse response = slack.methods().apiTest(ApiTestRequest.builder().foo("fine").build());
        assertThat(response.isOk(), is(true));
        assertThat(response.getArgs().getFoo(), is("fine"));
    }

    @Test
    public void error() throws IOException, SlackApiException {
        ApiTestResponse response = slack.methods().apiTest(ApiTestRequest.builder().error("error").build());
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is("error"));
        assertThat(response.getArgs().getError(), is("error"));
    }

}