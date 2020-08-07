package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.calls.CallsAddResponse;
import com.slack.api.methods.response.calls.CallsEndResponse;
import com.slack.api.methods.response.calls.CallsInfoResponse;
import com.slack.api.methods.response.calls.CallsUpdateResponse;
import com.slack.api.methods.response.calls.participants.CallsParticipantsAddResponse;
import com.slack.api.methods.response.calls.participants.CallsParticipantsRemoveResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.CallParticipant;
import com.slack.api.model.block.CallBlock;
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
import static org.junit.Assert.assertNotNull;

@Slf4j
public class calls_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    private void validateCallBlock(String botToken, ChatPostMessageResponse message) throws IOException, SlackApiException {
        validateCallBlockInLatestMessage(botToken, message.getChannel());
    }

    private void validateCallBlockInLatestMessage(String botToken, String channel) throws IOException, SlackApiException {
        CallBlock callBlock = (CallBlock)
                slack.methods(botToken).conversationsHistory(r -> r.channel(channel).limit(1))
                        .getMessages()
                        .get(0)
                        .getBlocks().get(0);
        assertNotNull(callBlock.getCall());
    }

    @Test
    public void testAll() throws Exception {
        String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
        String uniqueId = UUID.randomUUID().toString();
        String joinUrl = "https://www.example.com/calls-test";
        List<String> userIds = slack.methods(botToken).usersList(r -> r.limit(50))
                .getMembers().stream()
                .filter(u -> !u.isDeleted() && !u.isBot())
                .map(u -> u.getId())
                .collect(toList());
        CallsAddResponse callCreation = slack.methods(botToken).callsAdd(r -> r
                .title("Test call")
                .joinUrl(joinUrl)
                .desktopAppJoinUrl(joinUrl)
                .externalUniqueId(uniqueId)
                .externalDisplayId("ext-display-id-111")
                .users(Arrays.asList(
                        CallParticipant.builder()
                                .slackId(userIds.get(0))
                                .build(),
                        CallParticipant.builder()
                                .avatarUrl("https://assets.brandfolder.com/pmix53-32t4so-a6439g/original/slackbot.png")
                                .displayName("anonymous user 1")
                                .externalId("anon-111")
                                .build()
                ))
        );
        assertThat(callCreation.getError(), is(nullValue()));

        String callId = callCreation.getCall().getId();

        ChatPostMessageResponse message = slack.methods(botToken).chatPostMessage(r -> r
                .channel("#random")
                .blocks(asBlocks(call(c -> c.callId(callId))))
        );
        assertThat(message.getError(), is(nullValue()));

        validateCallBlock(botToken, message);

        CallsInfoResponse callInfo = slack.methods(botToken).callsInfo(r -> r.id(callId));
        assertThat(callInfo.getError(), is(nullValue()));

        CallsUpdateResponse update = slack.methods(botToken).callsUpdate(r -> r
                .id(callId)
                .joinUrl("https://www.example.com/updated-call")
                .desktopAppJoinUrl("https://www.example.com/updated-call-2")
        );
        assertThat(update.getError(), is(nullValue()));

        validateCallBlockInLatestMessage(botToken, message.getChannel());

        CallParticipant participant1 = CallParticipant.builder().slackId(userIds.get(1)).build();
        CallsParticipantsAddResponse addParticipants = slack.methods(botToken).callsParticipantsAdd(r -> r
                .id(callId)
                .users(Arrays.asList(
                        participant1,
                        CallParticipant.builder()
                                .avatarUrl("https://assets.brandfolder.com/pmix53-32t4so-a6439g/original/slackbot.png")
                                .displayName("anonymous user 2")
                                .externalId("anon-222")
                                .build()
                ))
        );
        assertThat(addParticipants.getError(), is(nullValue()));

        CallsParticipantsRemoveResponse participantsRemoval = slack.methods(botToken).callsParticipantsRemove(r ->
                r.id(callId).users(Arrays.asList(participant1)));
        assertThat(participantsRemoval.getError(), is(nullValue()));

        validateCallBlockInLatestMessage(botToken, message.getChannel());

        CallsEndResponse end = slack.methods(botToken).callsEnd(r -> r.id(callId).duration(234));
        assertThat(end.getError(), is(nullValue()));

        validateCallBlockInLatestMessage(botToken, message.getChannel());
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

        String uniqueId = UUID.randomUUID().toString();
        String joinUrl = "https://www.example.com/calls-test";
        List<String> userIds = slack.methods(botToken).usersList(r -> r.limit(50))
                .getMembers().stream()
                .filter(u -> !u.isDeleted() && !u.isBot())
                .map(u -> u.getId())
                .collect(toList());
        callCreation = slack.methods(botToken).callsAdd(r -> r
                .title("Test call")
                .joinUrl(joinUrl)
                .desktopAppJoinUrl(joinUrl)
                .externalUniqueId(uniqueId)
                .externalDisplayId("ext-display-id-111")
                .users(Arrays.asList(
                        CallParticipant.builder()
                                .slackId(userIds.get(0))
                                .build(),
                        CallParticipant.builder()
                                .avatarUrl("https://assets.brandfolder.com/pmix53-32t4so-a6439g/original/slackbot.png")
                                .displayName("anonymous user 1")
                                .externalId("anon-111")
                                .build()
                ))
        );
        assertThat(callCreation.getError(), is(nullValue()));
        String callId = callCreation.getCall().getId();

        CallsUpdateResponse update = slack.methods(botToken).callsUpdate(r -> r
                        .id(callId)
                //.joinUrl("https://www.example.com/updated-call")
        );
        assertThat(update.getError(), is(notNullValue()));

        CallsParticipantsAddResponse addParticipants = slack.methods(botToken).callsParticipantsAdd(r -> r
                .id(callId)
                .users(Arrays.asList(
                        CallParticipant.builder().slackId("U123").build()
                ))
        );
        assertThat(addParticipants.getError(), is(notNullValue()));

        CallsEndResponse end = slack.methods(botToken).callsEnd(r -> r.id("inivalid").duration(-1));
        assertThat(end.getError(), is(notNullValue()));
    }

}
