package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.im.*;
import com.github.seratch.jslack.api.methods.response.users.UsersListResponse;
import com.github.seratch.jslack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class im_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void operations() throws Exception {
        ImListResponse listResponse = slack.methods().imList(r -> r
                .token(token)
                .limit(2)
                .build());
        assertThat(listResponse.getError(), is(nullValue()));
        assertThat(listResponse.isOk(), is(true));
        assertThat(listResponse.getResponseMetadata(), is(notNullValue()));

        UsersListResponse usersListResponse = slack.methods().usersList(r -> r
                .token(token)
                .presence(true)
                .build());
        List<User> users = usersListResponse.getMembers();
        final String userId = users.get(0).getId();

        ImOpenResponse openResponse = slack.methods().imOpen(r -> r
                .token(token)
                .user(userId)
                .build());
        assertThat(openResponse.getError(), is(nullValue()));
        assertThat(openResponse.isOk(), is(true));

        String channelId = openResponse.getChannel().getId();

        // without ts (worked before but it gets to fail because ts is required as of Jan 2019)
        {
            ImMarkResponse markResponse = slack.methods().imMark(r -> r
                    .token(token)
                    .channel(channelId)
                    .build());
            assertThat(markResponse.isOk(), is(false));
            assertThat(markResponse.getError(), is("invalid_timestamp"));
        }

        ChatPostMessageResponse firstMessageResponse = slack.methods().chatPostMessage(r -> r
                .token(token)
                .channel(channelId)
                .text("Hi!").build());
        assertThat(firstMessageResponse.getError(), is(nullValue()));
        assertThat(firstMessageResponse.isOk(), is(true));

        // with ts
        {
            ImMarkResponse markResponse = slack.methods().imMark(r -> r
                    .token(token)
                    .channel(channelId)
                    .ts(firstMessageResponse.getTs())
                    .build());
            assertThat(markResponse.getError(), is(nullValue()));
            assertThat(markResponse.isOk(), is(true));
        }

        ChatPostMessageResponse threadReplyResponse = slack.methods().chatPostMessage(r -> r
                .token(token)
                .channel(channelId)
                .threadTs(firstMessageResponse.getTs())
                .text("Hi!").build());
        assertThat(threadReplyResponse.getError(), is(nullValue()));
        assertThat(threadReplyResponse.isOk(), is(true));

        ImRepliesResponse repliesResponse = slack.methods().imReplies(r -> r
                .token(token)
                .channel(channelId)
                .threadTs(threadReplyResponse.getMessage().getThreadTs())
                .build());
        assertThat(repliesResponse.getError(), is(nullValue()));
        assertThat(repliesResponse.isOk(), is(true));

        ImHistoryResponse historyResponse = slack.methods().imHistory(r -> r
                .token(token)
                .channel(channelId)
                .count(10)
                .build());
        assertThat(historyResponse.getError(), is(nullValue()));
        assertThat(historyResponse.isOk(), is(true));

        ImCloseResponse closeResponse = slack.methods().imClose(r -> r
                .token(token)
                .channel(channelId)
                .build());
        assertThat(closeResponse.getError(), is(nullValue()));
        assertThat(closeResponse.isOk(), is(true));
    }

}