package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.mpim.*;
import com.slack.api.methods.response.users.UsersListResponse;
import com.slack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class mpim_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

    @Test
    public void operations() throws IOException, SlackApiException {
        MpimListResponse listResponse = slack.methods().mpimList(r -> r.token(userToken));
        assertThat(listResponse.isOk(), is(true));

        UsersListResponse usersListResponse = slack.methods().usersList(r -> r.token(userToken).presence(true));
        List<User> users = usersListResponse.getMembers();
        List<String> userIds = new ArrayList<>();
        for (User u : users) {
            if (u.isDeleted() == false && u.isRestricted() == false) {
                if (userIds.size() < 3) {
                    userIds.add(u.getId());
                }
            }
        }

        MpimOpenResponse openResponse = slack.methods().mpimOpen(r -> r.token(userToken).users(userIds));
        assertThat(openResponse.getError(), is(nullValue()));
        assertThat(openResponse.isOk(), is(true));

        String channelId = openResponse.getGroup().getId();

        {
            MpimMarkResponse markResponse = slack.methods(userToken).mpimMark(r -> r.channel(channelId));
            // ts is missing
            assertThat(markResponse.getError(), is("internal_error"));
        }

        {
            MpimMarkResponse markResponse = slack.methods(userToken).mpimMark(r -> r
                    .channel(channelId)
                    .ts(openResponse.getGroup().getLatest().getTs()));
            assertThat(markResponse.getError(), is(nullValue()));
            assertThat(markResponse.isOk(), is(true));
        }

        MpimHistoryResponse historyResponse = slack.methods().mpimHistory(r -> r.token(userToken).channel(channelId).count(10));
        assertThat(historyResponse.getError(), is(nullValue()));
        assertThat(historyResponse.isOk(), is(true));

        ChatPostMessageResponse parentMessage = slack.methods(userToken).chatPostMessage(r ->
                r.channel(channelId).text("Hi there"));
        assertThat(parentMessage.getError(), is(nullValue()));

        ChatPostMessageResponse threadMessage = slack.methods(userToken).chatPostMessage(r ->
                r.channel(channelId).threadTs(parentMessage.getTs()).text("What's up?"));
        assertThat(threadMessage.getError(), is(nullValue()));

        MpimRepliesResponse repliesResponse = slack.methods(userToken).mpimReplies(r ->
                r.channel(channelId).threadTs(parentMessage.getTs()));
        assertThat(repliesResponse.getError(), is(nullValue()));
        assertThat(repliesResponse.isOk(), is(true));

        MpimCloseResponse closeResponse = slack.methods().mpimClose(r -> r.token(userToken).channel(channelId));
        assertThat(closeResponse.getError(), is(nullValue()));
        assertThat(closeResponse.isOk(), is(true));
    }

}
