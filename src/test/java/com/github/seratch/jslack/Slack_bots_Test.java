package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.bots.BotsInfoRequest;
import com.github.seratch.jslack.api.methods.response.bots.BotsInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class Slack_bots_Test {

    Slack slack = Slack.getInstance();

    @Test
    public void botsInfo() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        String bot = "B03E94MLG"; // hard coded
        BotsInfoResponse response = slack.methods().botsInfo(BotsInfoRequest.builder().token(token).bot(bot).build());
        assertThat(response.isOk(), is(true));
        assertThat(response.getBot(), is(notNullValue()));
    }

}