package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.search.SearchAllRequest;
import com.github.seratch.jslack.api.methods.request.search.SearchFilesRequest;
import com.github.seratch.jslack.api.methods.request.search.SearchMessagesRequest;
import com.github.seratch.jslack.api.methods.response.search.SearchAllResponse;
import com.github.seratch.jslack.api.methods.response.search.SearchFilesResponse;
import com.github.seratch.jslack.api.methods.response.search.SearchMessagesResponse;
import com.github.seratch.jslack.api.model.MatchedItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import testing.Constants;
import testing.SlackTestConfig;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class Slack_search_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void all() throws IOException, SlackApiException {
        SearchAllResponse response = slack.methods().searchAll(
                SearchAllRequest.builder().token(token).query("test").build());

        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void messages() throws IOException, SlackApiException {
        SearchMessagesResponse response = slack.methods().searchMessages(
                SearchMessagesRequest.builder().token(token).query("test").build());

        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));

        MatchedItem match = response.getMessages().getMatches().get(0);
        assertThat(match.getUser(), is(notNullValue()));
        assertThat(match.getUsername(), is(notNullValue()));
    }

    @Test
    public void files() throws IOException, SlackApiException {
        SearchFilesResponse response = slack.methods().searchFiles(
                SearchFilesRequest.builder().token(token).query("test").build());

        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));

        MatchedItem match = response.getFiles().getMatches().get(0);
        assertThat(match.getUser(), is(notNullValue()));
        assertThat(match.getUsername(), is(notNullValue()));
    }
}