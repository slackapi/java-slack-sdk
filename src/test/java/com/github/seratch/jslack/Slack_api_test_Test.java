package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.api.ApiTestRequest;
import com.github.seratch.jslack.api.methods.response.api.ApiTestResponse;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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

    @Ignore
    @Test
    public void proxy() throws IOException, SlackApiException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
        OkHttpClient okHttpClient = new OkHttpClient.Builder().proxy(proxy).build();
        SlackHttpClient slackHttpClient = new SlackHttpClient(okHttpClient);
        Slack slack = Slack.getInstance(slackHttpClient);

        ApiTestResponse response = slack.methods().apiTest(ApiTestRequest.builder().foo("proxy?").build());
        assertThat(response.isOk(), is(true));
        assertThat(response.getArgs().getFoo(), is("proxy?"));
    }

}