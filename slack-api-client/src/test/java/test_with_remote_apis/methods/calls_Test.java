package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.calls.CallsAddResponse;
import com.slack.api.methods.response.calls.CallsEndResponse;
import com.slack.api.methods.response.calls.CallsInfoResponse;
import com.slack.api.methods.response.calls.CallsUpdateResponse;
import com.slack.api.methods.response.calls.participants.CallsParticipantsAddResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.CallParticipant;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.call;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class calls_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void testAll() throws IOException, SlackApiException {
        String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
        String uniqueId = UUID.randomUUID().toString();
        String joinUrl = "https://www.example.com/calls-test";
        List<String> userIds = slack.methods(botToken).usersList(r -> r.limit(50))
                .getMembers().stream()
                .filter(u -> !u.isDeleted() && !u.isBot())
                .map(u -> u.getId())
                .collect(toList());
        CallsAddResponse callCreation = slack.methods(botToken).callsAdd(r -> r
                .externalUniqueId(uniqueId)
                .joinUrl(joinUrl)
                .title("Test call")
                .users(Arrays.asList(CallParticipant.builder().slackId(userIds.get(0)).build()))
        );
        assertThat(callCreation.getError(), is(nullValue()));

        String callId = callCreation.getCall().getId();

        ChatPostMessageResponse message = slack.methods(botToken).chatPostMessage(r -> r
                .channel("#random")
                .blocks(asBlocks(call(c -> c.callId(callId))))
        );
        assertThat(message.getError(), is(nullValue()));

        CallsInfoResponse callInfo = slack.methods(botToken).callsInfo(r -> r.id(callId));
        assertThat(callInfo.getError(), is(nullValue()));

        CallsUpdateResponse update = slack.methods(botToken).callsUpdate(r -> r
                .id(callId)
                .joinUrl("https://www.example.com/updated-call")
        );
        assertThat(update.getError(), is(nullValue()));

        CallsParticipantsAddResponse addParticipants = slack.methods(botToken).callsParticipantsAdd(r -> r
                .id(callId)
                .users(Arrays.asList(
                        CallParticipant.builder().slackId(userIds.get(1)).build()
                ))
        );
        assertThat(addParticipants.getError(), is(nullValue()));

        CallsEndResponse end = slack.methods(botToken).callsEnd(r -> r.id(callId).duration(234));
        assertThat(end.getError(), is(nullValue()));
    }

    @Test
    public void errors() throws IOException, SlackApiException {
        String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
        CallsAddResponse callCreation = slack.methods(botToken).callsAdd(r -> r
                .title("Test call")
        );
        assertThat(callCreation.getError(), is(notNullValue()));

        CallsInfoResponse callInfo = slack.methods(botToken).callsInfo(r -> r.id("dummy"));
        assertThat(callInfo.getError(), is(notNullValue()));

        CallsUpdateResponse update = slack.methods(botToken).callsUpdate(r -> r
                .id("dummy")
                .joinUrl("https://www.example.com/updated-call")
        );
        assertThat(update.getError(), is(notNullValue()));

        CallsParticipantsAddResponse addParticipants = slack.methods(botToken).callsParticipantsAdd(r -> r
                .id("dummy")
                .users(Arrays.asList(
                        CallParticipant.builder().slackId("U123").build()
                ))
        );
        assertThat(addParticipants.getError(), is(notNullValue()));

        CallsEndResponse end = slack.methods(botToken).callsEnd(r -> r.id("dummy").duration(234));
        assertThat(end.getError(), is(notNullValue()));
    }

}
