package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.*;
import com.github.seratch.jslack.api.methods.response.*;
import com.github.seratch.jslack.api.model.*;
import com.github.seratch.jslack.api.rtm.RTMClient;
import com.github.seratch.jslack.api.rtm.RTMMessageHandler;
import com.github.seratch.jslack.api.webhook.Payload;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class SlackTest {

    @Test
    public void incomingWebhook() throws IOException {
        // String url = "https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX";
        String url = System.getenv("SLACK_WEBHOOK_TEST_URL");

        Payload payload = Payload.builder()
                .channel("#random")
                .text("Hello World!")
                .iconEmoji(":smile_cat:")
                .username("jSlack")
                .attachments(new ArrayList<>())
                .build();

        Attachment attachment = Attachment.builder()
                .text("This is an attachment.")
                .authorName("Smiling Imp")
                .color("#36a64f")
                .fallback("Required plain-text summary of the attachment.")
                .title("Slack API Documentation")
                .titleLink("https://api.slack.com/")
                .footer("footer")
                .fields(new ArrayList<>())
                .build();
        {
            Field field = Field.builder()
                    .title("Long Title")
                    .value("Long Value........................................................")
                    .valueShortEnough(false).build();
            attachment.getFields().add(field);
            attachment.getFields().add(field);
        }
        {
            Field field = Field.builder()
                    .title("Short Title")
                    .value("Short Value")
                    .valueShortEnough(true).build();
            attachment.getFields().add(field);
            attachment.getFields().add(field);
        }
        payload.getAttachments().add(attachment);

        Response response = new Slack().send(url, payload);
        log.info(response.toString());
    }

    //    private static int SLEEP_MILLIS = 10000;
    private static int SLEEP_MILLIS = 100;

    @Test
    public void rtm() throws Exception {
        JsonParser jsonParser = new JsonParser();
        String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");
        Slack slack = new Slack();
        try (RTMClient rtm = slack.rtm(token)) {
            RTMMessageHandler handler1 = (message) -> {
                JsonObject json = jsonParser.parse(message).getAsJsonObject();
                if (json.get("type") != null) {
                    log.info("Handled type: {}", json.get("type").getAsString());
                }
            };
            RTMMessageHandler handler2 = (message) -> {
                log.info("Hello!");
            };
            rtm.addMessageHandler(handler1);
            rtm.addMessageHandler(handler1);
            rtm.addMessageHandler(handler2);
            rtm.connect();

            Thread.sleep(SLEEP_MILLIS);
            // Try anything on the channel...

            rtm.removeMessageHandler(handler2);

            Thread.sleep(SLEEP_MILLIS);
            // Try anything on the channel...

        }
    }

    @Test
    public void apiTest() throws IOException, SlackApiException {
        {
            Slack slack = new Slack();
            ApiTestResponse response = slack.methods().apiTest(ApiTestRequest.builder().foo("fine").build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getArgs().getFoo(), is("fine"));
        }
        {
            Slack slack = new Slack();
            ApiTestResponse response = slack.methods().apiTest(ApiTestRequest.builder().error("error").build());
            assertThat(response.isOk(), is(false));
            assertThat(response.getError(), is("error"));
            assertThat(response.getArgs().getError(), is("error"));
        }
    }

    @Test
    public void authRevoke() throws IOException, SlackApiException {
        {
            Slack slack = new Slack();
            AuthRevokeResponse response = slack.methods().authRevoke(AuthRevokeRequest.builder().token("dummy").test("1").build());
            assertThat(response.isOk(), is(false));
            assertThat(response.getError(), is("invalid_auth"));
            assertThat(response.isRevoked(), is(false));
        }
    }

    @Test
    public void authTest() throws IOException, SlackApiException {
        {
            Slack slack = new Slack();
            String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");
            AuthTestResponse response = slack.methods().authTest(AuthTestRequest.builder().token(token).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getUrl(), is(notNullValue()));
        }
    }

    @Test
    public void botsInfo() throws IOException, SlackApiException {
        {
            Slack slack = new Slack();
            String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");
            String bot = "B03E94MLG"; // hard coded
            BotsInfoResponse response = slack.methods().botsInfo(BotsInfoRequest.builder().token(token).bot(bot).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getBot(), is(notNullValue()));
        }
    }

    @Test
    public void channels_chat() throws IOException, SlackApiException {
        {
            Slack slack = new Slack();
            String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");

            {
                ChannelsListResponse response = slack.methods().channelsList(
                        ChannelsListRequest.builder().token(token).build());
                assertThat(response.isOk(), is(true));
                assertThat(response.getChannels(), is(notNullValue()));
            }

            ChannelsCreateResponse creationResponse = slack.methods().channelsCreate(
                    ChannelsCreateRequest.builder().token(token).name("test" + System.currentTimeMillis()).build());
            assertThat(creationResponse.isOk(), is(true));
            assertThat(creationResponse.getChannel(), is(notNullValue()));

            Channel channel = creationResponse.getChannel();

            {
                ChannelsInfoResponse response = slack.methods().channelsInfo(
                        ChannelsInfoRequest.builder().token(token).channel(channel.getId()).build());
                assertThat(response.isOk(), is(true));
                Channel fetchedChannel = response.getChannel();
                assertThat(fetchedChannel.isMember(), is(true));
                assertThat(fetchedChannel.isGeneral(), is(false));
                assertThat(fetchedChannel.isArchived(), is(false));
            }

            {
                ChannelsSetPurposeResponse response = slack.methods().channelsSetPurpose(
                        ChannelsSetPurposeRequest.builder().token(token).channel(channel.getId()).purpose("purpose").build());
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsSetTopicResponse response = slack.methods().channelsSetTopic(
                        ChannelsSetTopicRequest.builder().token(token).channel(channel.getId()).topic("topic").build());
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsHistoryResponse response = slack.methods().channelsHistory(
                        ChannelsHistoryRequest.builder().token(token).channel(channel.getId()).count(10).build());
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsKickResponse response = slack.methods().channelsKick(ChannelsKickRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .user(channel.getMembers().get(0))
                        .build());
                // TODO: valid test
                assertThat(response.isOk(), is(false));
                assertThat(response.getError(), is("cant_kick_self"));
            }

            {
                ChannelsInviteResponse response = slack.methods().channelsInvite(ChannelsInviteRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .user(channel.getMembers().get(0))
                        .build());
                // TODO: valid test
                assertThat(response.isOk(), is(false));
                assertThat(response.getError(), is("cant_invite_self"));
            }

            {
                ChatMeMessageResponse response = slack.methods().chatMeMessage(ChatMeMessageRequest.builder()
                        .channel(channel.getId())
                        .token(token)
                        .text("Hello World! via chat.meMessage API")
                        .build());
                assertThat(response.isOk(), is(true));
            }

            {
                ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                        .channel(channel.getId())
                        .token(token)
                        .text("@seratch Hello World! via chat.postMessage API")
                        .linkNames(1)
                        .build());
                assertThat(postResponse.isOk(), is(true));

                ChatUpdateResponse updateResponse = slack.methods().chatUpdate(ChatUpdateRequest.builder()
                        .channel(channel.getId())
                        .token(token)
                        .ts(postResponse.getTs())
                        .text("Updated text")
                        .linkNames(0)
                        .build());
                assertThat(updateResponse.isOk(), is(true));

                ChatDeleteResponse deleteResponse = slack.methods().chatDelete(ChatDeleteRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .ts(postResponse.getMessage().getTs())
                        .build());
                assertThat(deleteResponse.isOk(), is(true));
            }

            {
                ChannelsLeaveResponse response = slack.methods().channelsLeave(ChannelsLeaveRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .build());
                assertThat(response.isOk(), is(true));
            }
            {
                ChannelsJoinResponse response = slack.methods().channelsJoin(ChannelsJoinRequest.builder()
                        .token(token)
                        .name(channel.getName())
                        .build());
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsRenameResponse response = slack.methods().channelsRename(ChannelsRenameRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .name(channel.getName() + "-1")
                        .build());
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsArchiveResponse response = slack.methods().channelsArchive(
                        ChannelsArchiveRequest.builder().token(token).channel(channel.getId()).build());
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsInfoResponse response = slack.methods().channelsInfo(
                        ChannelsInfoRequest.builder().token(token).channel(channel.getId()).build());
                assertThat(response.isOk(), is(true));
                Channel fetchedChannel = response.getChannel();
                assertThat(fetchedChannel.isMember(), is(false));
                assertThat(fetchedChannel.isGeneral(), is(false));
                assertThat(fetchedChannel.isArchived(), is(true));
            }
        }
    }

    @Test
    public void dnd() throws IOException, SlackApiException {
        Slack slack = new Slack();
        String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");

        List<User> members = slack.methods().usersList(UsersListRequest.builder().token(token).presence(1).build()).getMembers();
        {
            String user = members.get(0).getId();
            DndInfoResponse response = slack.methods().dndInfo(DndInfoRequest.builder().token(token).user(user).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getNextDndStartTs(), is(notNullValue()));
        }

        {
            List<String> users = members.stream().map(m -> m.getId()).collect(toList());
            DndTeamInfoResponse response = slack.methods().dndTeamInfo(DndTeamInfoRequest.builder().token(token).users(users).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getUsers(), is(notNullValue()));
        }
    }

    @Test
    public void emoji() throws IOException, SlackApiException {
        Slack slack = new Slack();
        String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");

        {
            EmojiListResponse response = slack.methods().emojiList(EmojiListRequest.builder().token(token).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getEmoji(), is(notNullValue()));
        }
    }

    @Test
    public void groups() throws IOException, SlackApiException {
        Slack slack = new Slack();
        String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");

        GroupsCreateResponse creationResponse = slack.methods().groupsCreate(
                GroupsCreateRequest.builder().token(token).name("secret-" + System.currentTimeMillis()).build());
        Group group = creationResponse.getGroup();
        {
            assertThat(creationResponse.isOk(), is(true));
            assertThat(creationResponse.getGroup(), is(notNullValue()));
        }

        {
            GroupsHistoryResponse response = slack.methods().groupsHistory(
                    GroupsHistoryRequest.builder().token(token).channel(group.getId()).build());
            assertThat(response.isOk(), is(true));
        }
        {
            GroupsInfoResponse response = slack.methods().groupsInfo(
                    GroupsInfoRequest.builder().token(token).channel(group.getId()).build());
            assertThat(response.isOk(), is(true));
        }

        GroupsCreateChildResponse childCreationResponse = slack.methods().groupsCreateChild(
                GroupsCreateChildRequest.builder().token(token).channel(group.getId()).build());
        group = childCreationResponse.getGroup();
        {
            assertThat(childCreationResponse.isOk(), is(true));
        }

        {
            GroupsArchiveResponse response = slack.methods().groupsArchive(
                    GroupsArchiveRequest.builder().token(token).channel(group.getId()).build());
            assertThat(response.isOk(), is(true));
        }

        {
            GroupsCloseResponse response = slack.methods().groupsClose(
                    GroupsCloseRequest.builder().token(token).channel(group.getId()).build());
            assertThat(response.isOk(), is(true));
        }
    }

    @Test
    public void users() throws IOException, SlackApiException {
        Slack slack = new Slack();
        String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");

        {
            UsersSetPresenceResponse response = slack.methods().usersSetPresence(
                    UsersSetPresenceRequest.builder().token(token).presence("away").build());
            assertThat(response.isOk(), is(true));
        }

        {
            UsersSetActiveResponse response = slack.methods().usersSetActive(
                    UsersSetActiveRequest.builder().token(token).build());
            assertThat(response.isOk(), is(true));
        }

        {
            UsersIdentityResponse response = slack.methods().usersIdentity(UsersIdentityRequest.builder().token(token).build());
            // TODO: test preparation?
            // {"ok":false,"error":"missing_scope","needed":"identity.basic","provided":"identify,read,post,client,apps,admin"}
            assertThat(response.isOk(), is(false));
        }

        UsersListResponse usersListResponse = slack.methods().usersList(UsersListRequest.builder().token(token).presence(1).build());
        List<User> users = usersListResponse.getMembers();
        String userId = users.get(0).getId();

        {
            assertThat(usersListResponse.isOk(), is(true));
            assertThat(users, is(notNullValue()));
            User user = users.get(0);
            assertThat(user.getId(), is(notNullValue()));
            assertThat(user.getName(), is(notNullValue()));
            assertThat(user.getProfile().getImage24(), is(notNullValue()));
            assertThat(user.getProfile().getImage32(), is(notNullValue()));
            assertThat(user.getProfile().getImage48(), is(notNullValue()));
            assertThat(user.getProfile().getImage72(), is(notNullValue()));
            assertThat(user.getProfile().getImage192(), is(notNullValue()));
            assertThat(user.getProfile().getImage512(), is(notNullValue()));
        }

        {
            UsersInfoResponse response = slack.methods().usersInfo(UsersInfoRequest.builder().token(token).user(userId).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getUser(), is(notNullValue()));
        }

        {
            UsersGetPresenceResponse response = slack.methods().usersGetPresence(
                    UsersGetPresenceRequest.builder().token(token).user(userId).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getPresence(), is(notNullValue()));
        }
    }

    @Test
    public void usersProfile() throws IOException, SlackApiException {
        Slack slack = new Slack();
        String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");

        {
            UsersProfileGetResponse response = slack.methods().usersProfileGet(UsersProfileGetRequest.builder().token(token).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getProfile(), is(notNullValue()));
        }

        {
            UsersProfileSetResponse response = slack.methods().usersProfileSet(
                    UsersProfileSetRequest.builder().token(token).name("skype").value("skype-" + System.currentTimeMillis()).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getProfile(), is(notNullValue()));
        }

        {
            User.Profile profile = new User.Profile();
            profile.setSkype("skype-" + System.currentTimeMillis());
            UsersProfileSetResponse response = slack.methods().usersProfileSet(
                    UsersProfileSetRequest.builder().token(token).profile(profile).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getProfile(), is(notNullValue()));
        }
    }

}