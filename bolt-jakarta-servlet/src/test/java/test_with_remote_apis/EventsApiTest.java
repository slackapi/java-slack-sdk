package test_with_remote_apis;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.middleware.Middleware;
import com.slack.api.methods.request.files.FilesUploadRequest;
import com.slack.api.methods.response.apps.event.authorizations.AppsEventAuthorizationsListResponse;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.methods.response.chat.ChatDeleteResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.chat.ChatUpdateResponse;
import com.slack.api.methods.response.conversations.*;
import com.slack.api.methods.response.dnd.DndEndSnoozeResponse;
import com.slack.api.methods.response.dnd.DndSetSnoozeResponse;
import com.slack.api.methods.response.files.FilesDeleteResponse;
import com.slack.api.methods.response.files.FilesUploadResponse;
import com.slack.api.methods.response.pins.PinsAddResponse;
import com.slack.api.methods.response.pins.PinsRemoveResponse;
import com.slack.api.methods.response.reactions.ReactionsAddResponse;
import com.slack.api.methods.response.reactions.ReactionsRemoveResponse;
import com.slack.api.methods.response.stars.StarsAddResponse;
import com.slack.api.methods.response.stars.StarsRemoveResponse;
import com.slack.api.methods.response.usergroups.UsergroupsCreateResponse;
import com.slack.api.methods.response.usergroups.users.UsergroupsUsersUpdateResponse;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.event.*;
import com.slack.api.util.json.GsonFactory;
import config.Constants;
import config.SlackTestConfig;
import lombok.Data;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import test_with_remote_apis.sample_json_generation.EventDataRecorder;
import util.ResourceLoader;
import util.TestSlackAppServer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class EventsApiTest {

    Map<String, String> configValues = new HashMap<>();
    SlackConfig recorderSlackConfig = SlackTestConfig.getInstance().getConfig();
    SlackConfig slackConfig = new SlackConfig();
    Slack recorderSlack = null;
    Slack slack = null;
    AppConfig appConfig = ResourceLoader.loadAppConfig();
    Gson gson = null;

    EventDataRecorder recorder = new EventDataRecorder("../json-logs");

    private Middleware recorderMiddleware() {
        return (req, resp, chain) -> {
            String body = req.getRequestBodyAsString();
            if (body.startsWith("{")) {
                JsonElement json = gson.fromJson(body, JsonElement.class);
                JsonElement event = json.getAsJsonObject().get("event");
                if (event != null) {
                    String eventType = event.getAsJsonObject().get("type").getAsString();
                    String payload = gson.toJson(json);
                    if (eventType.equals("message")) {
                        JsonElement subtype = event.getAsJsonObject().get("subtype");
                        if (subtype == null) {
                            recorder.writeMergedResponse(eventType, payload);
                        } else {
                            // TODO
                        }
                    } else {
                        recorder.writeMergedResponse(eventType, payload);
                    }
                    req.getContext().logger.info("Event payload: {}", payload);
                }
            }
            return chain.next(req);
        };
    }

    private void waitForSlackAppConnection() throws IOException, InterruptedException {
        String publicUrl = configValues.get("publicUrl");
        if (publicUrl == null) {
            throw new IllegalStateException("To run this test, set publicUrl in src/test/resources/appConfig.json");
        }
        String subdomain = publicUrl.split("\\.")[0].replaceFirst("https://", "");
        OkHttpClient httpClient = new OkHttpClient();
        Request req = new Request.Builder().post(FormBody.create("ssl_check=1".getBytes())).url(publicUrl).build();

        Response resp = httpClient.newCall(req).execute();
        while (resp.code() != 200) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
                    "\n" +
                    "Run the following:\n" +
                    "\n" +
                    "  ngrok http 3000 --subdomain " + subdomain + "\n" +
                    "\n" +
                    "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
            Thread.sleep(3000);
            resp = httpClient.newCall(req).execute();
        }

        Thread.sleep(500L);
    }

    @Before
    public void setup() {
        recorderSlackConfig.setPrettyResponseLoggingEnabled(true);
        recorderSlack = Slack.getInstance(recorderSlackConfig);
        slack = Slack.getInstance(slackConfig);
        configValues = ResourceLoader.loadValues();
        appConfig.setSigningSecret(configValues.get("signingSecret"));
        appConfig.setSingleTeamBotToken(configValues.get("singleTeamBotToken"));
        gson = GsonFactory.createSnakeCase(recorderSlackConfig);
    }

    @Data
    public static class ChannelTestState {
        private boolean channelCreated;
        private boolean memberJoinedChannel;
        private boolean memberLeftChannel;
        private boolean channelLeft;
        private boolean pinAdded;
        private boolean pinRemoved;
        private boolean channelRenamed;
        private boolean channelShared;
        private boolean channelUnshared;
        private boolean channelArchive;
        private boolean channelUnarchive;
        private boolean appMention;
        private boolean linkShared;
        private boolean entityDetailsRequested;
        private boolean message;
        private boolean reactionAdded;
        private boolean reactionRemoved;
        private boolean starAdded;
        private boolean starRemoved;
        private boolean fileCreated;
        private boolean fileDeleted;
        private boolean filePublic;
        private boolean fileShared;
        private boolean fileUnshared;

        public boolean isAllDone() {
            return channelCreated
                    && memberJoinedChannel && memberLeftChannel
                    && channelLeft
                    && channelRenamed
                    // TODO
                    // && channelShared && channelUnshared
                    && channelArchive && channelUnarchive
                    && pinAdded && pinRemoved
                    && appMention && message
                    // TODO
                    // && linkShared
                    && reactionAdded && reactionRemoved
                    && starAdded && starRemoved
                    && fileCreated && fileDeleted && filePublic && fileShared && fileUnshared;
        }
    }

    @Test
    public void publicChannelsAndInteractions() throws Exception {

        App app = new App(appConfig);
        app.use(recorderMiddleware());

        String publicChannelId = null;
        String botToken = appConfig.getSingleTeamBotToken();
        String userToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_USER_TOKEN);

        TestSlackAppServer server = new TestSlackAppServer(app);
        ChannelTestState state = new ChannelTestState();
        try {

            // ----------------------------
            // Public channels
            // ----------------------------
            // channel_created
            app.event(ChannelCreatedEvent.class, (req, ctx) -> {
                state.setChannelCreated(true);
                return ctx.ack();
            });

            // member_joined_channel
            app.event(MemberJoinedChannelEvent.class, (req, ctx) -> {
                state.setMemberJoinedChannel(true);
                return ctx.ack();
            });

            // member_left_channel
            app.event(MemberLeftChannelEvent.class, (req, ctx) -> {
                state.setMemberLeftChannel(true);
                return ctx.ack();
            });

            // pin_added
            app.event(PinAddedEvent.class, (req, ctx) -> {
                state.setPinAdded(true);
                return ctx.ack();
            });
            // pin_removed
            app.event(PinRemovedEvent.class, (req, ctx) -> {
                state.setPinRemoved(true);
                return ctx.ack();
            });
            // channel_rename
            app.event(ChannelRenameEvent.class, (req, ctx) -> {
                state.setChannelRenamed(true);
                return ctx.ack();
            });
            // channel_left
            app.event(ChannelLeftEvent.class, (req, ctx) -> {
                state.setChannelLeft(true);
                return ctx.ack();
            });
            // channel_shared
            app.event(ChannelSharedEvent.class, (req, ctx) -> {
                state.setChannelShared(true);
                return ctx.ack();
            });
            // channel_unshared
            app.event(ChannelUnsharedEvent.class, (req, ctx) -> {
                state.setChannelUnshared(true);
                return ctx.ack();
            });
            // channel_archive
            app.event(ChannelArchiveEvent.class, (req, ctx) -> {
                state.setChannelArchive(true);
                return ctx.ack();
            });
            // channel_unarchive
            app.event(ChannelUnarchiveEvent.class, (req, ctx) -> {
                state.setChannelUnarchive(true);
                return ctx.ack();
            });

            // ----------------------------
            // Interaction
            // ----------------------------
            // app_mention
            app.event(AppMentionEvent.class, (req, ctx) -> {
                state.setAppMention(true);
                return ctx.ack();
            });

            // link_shared
            app.event(LinkSharedEvent.class, (req, ctx) -> {
                state.setLinkShared(true);
                return ctx.ack();
            });

            // entity_details_requested
            app.event(EntityDetailsRequestedEvent.class, (req, ctx) -> {
                state.setEntityDetailsRequested(true);
                return ctx.ack();
            });

            // message
            app.event(MessageEvent.class, (req, ctx) -> {
                state.setMessage(true);
                return ctx.ack();
            });

            // reaction_added
            app.event(ReactionAddedEvent.class, (req, ctx) -> {
                state.setReactionAdded(true);
                return ctx.ack();
            });
            // reaction_removed
            app.event(ReactionRemovedEvent.class, (req, ctx) -> {
                state.setReactionRemoved(true);
                return ctx.ack();
            });

            // star_added
            app.event(StarAddedEvent.class, (req, ctx) -> {
                state.setStarAdded(true);
                return ctx.ack();
            });
            // star_removed
            app.event(StarRemovedEvent.class, (req, ctx) -> {
                state.setStarRemoved(true);
                return ctx.ack();
            });

            // ----------------------------
            // Files
            // ----------------------------
            // file_created
            app.event(FileCreatedEvent.class, (req, ctx) -> {
                state.setFileCreated(true);
                return ctx.ack();
            });
            // file_deleted
            app.event(FileDeletedEvent.class, (req, ctx) -> {
                state.setFileDeleted(true);
                return ctx.ack();
            });
            // file_public
            app.event(FilePublicEvent.class, (req, ctx) -> {
                state.setFilePublic(true);
                return ctx.ack();
            });
            // file_shared
            app.event(FileSharedEvent.class, (req, ctx) -> {
                state.setFileShared(true);
                return ctx.ack();
            });
            // file_unshared
            app.event(FileUnsharedEvent.class, (req, ctx) -> {
                state.setFileUnshared(true);
                return ctx.ack();
            });

            // ------------------------------------------------------------------------------------

            server.startAsDaemon();

            waitForSlackAppConnection();

            // ------------------------------------------------------------------------------------

            // channel_created
            ConversationsCreateResponse publicChannel =
                    slack.methods(botToken).conversationsCreate(r -> r.name("test-" + System.currentTimeMillis()).isPrivate(false));
            assertNull(publicChannel.getError());

            publicChannelId = publicChannel.getChannel().getId();
            AuthTestResponse botAuthTest = slack.methods().authTest(r -> r.token(botToken));
            String botUserId = botAuthTest.getUserId();
            {
                final String channelId = publicChannelId;

                // member_joined_channel
                ConversationsJoinResponse joining = slack.methods(userToken).conversationsJoin(r -> r.channel(channelId));
                assertNull(joining.getError());
                // member_left_channel
                // channel_left
                ConversationsLeaveResponse leaving = slack.methods(userToken).conversationsLeave(r -> r.channel(channelId));
                assertNull(leaving.getError());

                // join again for the further steps
                joining = slack.methods(userToken).conversationsJoin(r -> r.channel(channelId));
                assertNull(joining.getError());

                // app_mention
                String mentionText = "<@" + botUserId + "> Hello!";
                ChatPostMessageResponse mention = slack.methods(userToken).chatPostMessage(r ->
                        r.text(mentionText).channel(channelId));
                assertNull(mention.getError());
                ChatUpdateResponse editedMention = slack.methods(userToken).chatUpdate(r -> r
                        .channel(channelId)
                        .ts(mention.getTs())
                        .text(mentionText + "(edited)")
                );
                assertNull(editedMention.getError());

                // app_mention with blocks
                ChatPostMessageResponse mention2 = slack.methods(userToken).chatPostMessage(r ->
                        r.text(mentionText).blocks(Arrays.asList(
                                SectionBlock.builder()
                                        .blockId("block-id-value")
                                        // this needs to be a mrkdwn-type text object
                                        .text(MarkdownTextObject.builder().text(mentionText).build())
                                        .build()
                        )).channel(channelId));
                assertNull(mention2.getError());

                // link_shared
                // NOTE: Add "www.youtube.com" to Event Subscriptions > App unfurl domains
                ChatPostMessageResponse linkShared = slack.methods(userToken).chatPostMessage(r -> r
                        .channel(channelId)
                        .text("<https://www.youtube.com/watch?v=9RJZMSsH7-g|Learn about Slack>")
                        .asUser(true)
                        .unfurlLinks(true)
                        .unfurlMedia(true)
                );
                assertNull(linkShared.getError());

                String linkMessageTs = linkShared.getTs();

                // reaction_added
                ReactionsAddResponse reactionCreation =
                        slack.methods(userToken).reactionsAdd(r -> r.channel(channelId).timestamp(linkMessageTs).name("eyes"));
                assertNull(reactionCreation.getError());
                // reaction_removed
                ReactionsRemoveResponse reactionRemoval =
                        slack.methods(userToken).reactionsRemove(r -> r.channel(channelId).timestamp(linkMessageTs).name("eyes"));
                assertNull(reactionRemoval.getError());

                // star_added
                StarsAddResponse starCreation = slack.methods(userToken).starsAdd(r -> r.channel(channelId).timestamp(linkMessageTs));
                assertNull(starCreation.getError());
                // star_removed
                StarsRemoveResponse starRemoval = slack.methods(userToken).starsRemove(r -> r.channel(channelId).timestamp(linkMessageTs));
                assertNull(starRemoval.getError());

                // file_created
                // file_public
                // file_shared
                FilesUploadResponse fileUpload = slack.methods(botToken).filesUpload(r -> r
                        .title("Sample Text")
                        .content("This is a text file.")
                        .channels(Arrays.asList(channelId))
                        .initialComment("This is a test.")
                        .filename("sample.txt")
                );
                assertNull(fileUpload.getError());

                String fileId = fileUpload.getFile().getId();
                String fileShareTs = fileUpload.getFile().getShares().getPublicChannels().get(channelId).get(0).getTs();

                // file_unshared
                ChatDeleteResponse fileUnsharing = slack.methods(botToken).chatDelete(r -> r.channel(channelId).ts(fileShareTs));
                assertNull(fileUnsharing.getError());

                // file_deleted
                FilesDeleteResponse fileDeletion = slack.methods(botToken).filesDelete(r -> r.file(fileId));
                assertNull(fileDeletion.getError());

                // pin_added
                String pinnedMessageTs = slack.methods(botToken).chatPostMessage(r -> r
                                .channel(channelId)
                                .text("This is really *important*.")
                        // TODO
                        //.blocks(SampleObjects.Blocks)
                ).getTs();
                // Adding to pinned items needs to be done by another user.
                PinsAddResponse pinCreation = slack.methods(userToken).pinsAdd(r -> r.channel(channelId).timestamp(pinnedMessageTs));
                assertNull(pinCreation.getError());
                // pin_removed
                // Removing from pinned items needs to be done by another user.
                PinsRemoveResponse pinRemoval = slack.methods(userToken).pinsRemove(r -> r.channel(channelId).timestamp(pinnedMessageTs));
                assertNull(pinRemoval.getError());

                // channel_rename
                ConversationsRenameResponse renaming =
                        slack.methods(botToken).conversationsRename(r -> r.channel(channelId).name(publicChannel.getChannel().getName() + "-2"));
                assertNull(renaming.getError());

                // TODO
                /*
                String teamId = botAuthTest.getTeamId();
                String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);

                // channel_shared
                List<AdminTeamsListResponse.Team> workspaces = slack.methods(orgAdminUserToken).adminTeamsList(r -> r.limit(3)).getTeams();
                List<String> teamIds = workspaces.stream().map(t -> t.getId()).collect(toList());
                if (!teamIds.contains(teamId)) {
                    teamIds.add(teamId);
                }
                AdminConversationsSetTeamsResponse sharing =
                        slack.methods(orgAdminUserToken).adminConversationsSetTeams(r -> r
                                .teamId(teamId)
                                .channelId(channelId)
                                .targetTeamIds(teamIds)
                        );
                assertNull(sharing.getError());

                Thread.sleep(5000L);

                // channel_unshared
                teamIds.clear();
                teamIds.add(teamId);
                AdminConversationsSetTeamsResponse unsharing =
                        slack.methods(orgAdminUserToken).adminConversationsSetTeams(r -> r
                                //.teamId(teamId)
                                .targetTeamIds(teamIds)
                                .channelId(channelId));
                assertNull(unsharing.getError());
                Thread.sleep(5000L);

                 */

                // join for sure before archiving
                ConversationsJoinResponse rejoining = slack.methods(userToken).conversationsJoin(r -> r.channel(channelId));
                assertNull(rejoining.getError());
                // channel_archive
                ConversationsArchiveResponse archive = slack.methods(userToken).conversationsArchive(r -> r.channel(channelId));
                assertNull(archive.getError());
                // channel_unarchive
                ConversationsUnarchiveResponse unarchive = slack.methods(userToken).conversationsUnarchive(r -> r.channel(channelId));
                assertNull(unarchive.getError());
            }

            // ------------------------------------------------------------------------------------

            long waitTime = 0;
            while (!state.isAllDone() && waitTime < 50_000L) {
                long sleepTime = 100L;
                Thread.sleep(sleepTime);
                waitTime += sleepTime;
            }
            assertTrue(state.toString(), state.isAllDone());

        } finally {
            server.stop();

            if (publicChannelId != null) {
                String channelId = publicChannelId;
                slack.methods(botToken).conversationsArchive(r -> r.channel(channelId));
            }
        }
    }

    // ----------------------------
    // Private channels
    // ----------------------------

    @Data
    public static class GroupTestState {
        private boolean groupOpen;
        private boolean groupRename;
        private boolean groupLeft;
        private boolean groupClose;
        private boolean groupArchive;
        private boolean groupUnarchive;

        public boolean isAllDone() {
            return groupRename
                    && groupLeft
                    //&& groupOpen && groupClose
                    && groupArchive
                    //&& groupUnarchive
                    ;
        }
    }

    @Test
    public void privateChannels() throws Exception {

        App app = new App(appConfig);
        app.use(recorderMiddleware());

        String privateChannelId = null;
        String botToken = appConfig.getSingleTeamBotToken();

        TestSlackAppServer server = new TestSlackAppServer(app);
        GroupTestState state = new GroupTestState();
        try {

            // group_open
            app.event(GroupOpenEvent.class, (req, ctx) -> {
                state.setGroupOpen(true);
                return ctx.ack();
            });
            // group_rename
            app.event(GroupRenameEvent.class, (req, ctx) -> {
                state.setGroupRename(true);
                return ctx.ack();
            });
            // group_left
            app.event(GroupLeftEvent.class, (req, ctx) -> {
                state.setGroupLeft(true);
                return ctx.ack();
            });
            // group_close
            app.event(GroupCloseEvent.class, (req, ctx) -> {
                state.setGroupClose(true);
                return ctx.ack();
            });
            // group_archive
            app.event(GroupArchiveEvent.class, (req, ctx) -> {
                state.setGroupArchive(true);
                return ctx.ack();
            });
            // group_unarchive
            app.event(GroupUnarchiveEvent.class, (req, ctx) -> {
                state.setGroupUnarchive(true);
                return ctx.ack();
            });
            app.event(MemberJoinedChannelEvent.class, (req, ctx) -> ctx.ack());
            app.event(MemberLeftChannelEvent.class, (req, ctx) -> ctx.ack());
            app.event(MessageEvent.class, (req, ctx) -> ctx.ack());
            app.event(MessageChannelJoinEvent.class, (req, ctx) -> ctx.ack());
            app.event(MessageChannelArchiveEvent.class, (req, ctx) -> ctx.ack());
            app.event(MessageChannelUnarchiveEvent.class, (req, ctx) -> ctx.ack());
            app.event(MessageChannelNameEvent.class, (req, ctx) -> ctx.ack());

            // ------------------------------------------------------------------------------------

            server.startAsDaemon();

            waitForSlackAppConnection();

            // ------------------------------------------------------------------------------------

            String userToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
            String userId = slack.methods().authTest(r -> r.token(userToken)).getUserId();

            // group_open
            ConversationsCreateResponse privateChannel = slack.methods(botToken).conversationsCreate(r -> r
                    .name("private-test-" + System.currentTimeMillis())
                    .isPrivate(true)
            );
            assertNull(privateChannel.getError());

            privateChannelId = privateChannel.getChannel().getId();
            {

                final String channelId = privateChannelId;
                // group_rename
                ConversationsRenameResponse renaming =
                        slack.methods(botToken).conversationsRename(r -> r.channel(channelId).name(privateChannel.getChannel().getName() + "-2"));
                assertNull(renaming.getError());

                // group_close
                // group_archive
                ConversationsArchiveResponse archive = slack.methods(botToken).conversationsArchive(r -> r.channel(channelId));
                assertNull(archive.getError());
                // group_unarchive
                ConversationsUnarchiveResponse unarchive = slack.methods(botToken).conversationsUnarchive(r -> r.channel(channelId));
                assertNull(unarchive.getError());

                // group_left
                ConversationsInviteResponse invitation =
                        slack.methods(botToken).conversationsInvite(r -> r.channel(channelId).users(Arrays.asList(userId)));
                assertNull(invitation.getError());
                ConversationsKickResponse removal =
                        slack.methods(botToken).conversationsKick(r -> r.channel(channelId).user(userId));
                assertNull(removal.getError());

            }

            // ------------------------------------------------------------------------------------
            long waitTime = 0;
            while (!state.isAllDone() && waitTime < 10_000L) {
                long sleepTime = 100L;
                Thread.sleep(sleepTime);
                waitTime += sleepTime;
            }
            assertTrue(state.toString(), state.isAllDone());

            // for accepting other events
            Thread.sleep(5_000L);

        } finally {
            server.stop();

            if (privateChannelId != null) {
                String channelId = privateChannelId;
                slack.methods(botToken).conversationsArchive(r -> r.channel(channelId));
            }
        }
    }

    // ----------------------------
    // DM
    // ----------------------------

    // im_open
    // im_created
    // im_close

    @Data
    public static class ImTestState {
        private boolean imOpen;
        private boolean imCreated;
        private boolean imClose;

        public boolean isAllDone() {
            return imOpen
                    // && imCreated
                    && imClose;
        }
    }

    @Ignore // TODO fix this
    @Test
    public void im() throws Exception {

        App app = new App(appConfig);
        app.use(recorderMiddleware());

        String imId = null;
        String botToken = appConfig.getSingleTeamBotToken();

        TestSlackAppServer server = new TestSlackAppServer(app);
        ImTestState state = new ImTestState();
        try {

            // im_open
            app.event(ImOpenEvent.class, (req, ctx) -> {
                state.setImOpen(true);
                return ctx.ack();
            });
            // im_created
            app.event(ImCreatedEvent.class, (req, ctx) -> {
                state.setImCreated(true);
                return ctx.ack();
            });
            // im_close
            app.event(ImCloseEvent.class, (req, ctx) -> {
                state.setImClose(true);
                return ctx.ack();
            });

            // ------------------------------------------------------------------------------------

            server.startAsDaemon();

            waitForSlackAppConnection();

            // ------------------------------------------------------------------------------------

            String userToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_USER_TOKEN);
            String userId = slack.methods().authTest(r -> r.token(userToken)).getUserId();

            // im_open
            // im_created
            ConversationsOpenResponse opening = slack.methods(botToken).conversationsOpen(r -> r
                    .users(Arrays.asList(userId))
                    .returnIm(true)
            );
            assertNull(opening.getError());

            imId = opening.getChannel().getId();

            // im_close
            ConversationsCloseResponse closure = slack.methods(botToken).conversationsClose(r -> r.
                    channel(opening.getChannel().getId())
            );
            assertNull(closure.getError());

            // ------------------------------------------------------------------------------------

            long waitTime = 0;
            while (!state.isAllDone() && waitTime < 10_000L) {
                long sleepTime = 100L;
                Thread.sleep(sleepTime);
                waitTime += sleepTime;
            }
            assertTrue(state.toString(), state.isAllDone());

        } finally {
            server.stop();

            if (imId != null) {
                String channelId = imId;
                slack.methods(botToken).conversationsArchive(r -> r.channel(channelId));
            }
        }
    }

    // ----------------------------
    // Usergroups
    // ----------------------------

    // subteam_created
    // subteam_members_changed
    // subteam_self_added
    // subteam_self_removed
    // subteam_updated

    @Data
    public static class SubteamTestState {
        private boolean subteamCreated;
        private boolean subteamMembersChanged;
        private boolean subteamSelfAdded;
        private boolean subteamSelfRemoved;
        private boolean subteamUpdated;

        public boolean isAllDone() {
            return subteamCreated
                    && subteamMembersChanged
                    && subteamSelfAdded
                    && subteamSelfRemoved
                    // Started failing on May 12, 2021
                    // && subteamUpdated
                    ;
        }
    }

    @Test
    public void usergroups() throws Exception {

        App app = new App(appConfig);
        app.use(recorderMiddleware());

        String botToken = appConfig.getSingleTeamBotToken();
        String userToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_USER_TOKEN);

        TestSlackAppServer server = new TestSlackAppServer(app);
        SubteamTestState state = new SubteamTestState();

        String createdUsergroupId = null;
        try {

            // subteam_created
            app.event(SubteamCreatedEvent.class, (req, ctx) -> {
                state.setSubteamCreated(true);
                return ctx.ack();
            });
            // subteam_members_changed
            app.event(SubteamMembersChangedEvent.class, (req, ctx) -> {
                state.setSubteamMembersChanged(true);
                return ctx.ack();
            });
            // subteam_self_added
            app.event(SubteamSelfAddedEvent.class, (req, ctx) -> {
                state.setSubteamSelfAdded(true);
                return ctx.ack();
            });
            // subteam_self_removed
            app.event(SubteamSelfRemovedEvent.class, (req, ctx) -> {
                state.setSubteamSelfRemoved(true);
                return ctx.ack();
            });
            // subteam_updated
            app.event(SubteamUpdatedEvent.class, (req, ctx) -> {
                state.setSubteamUpdated(true);
                return ctx.ack();
            });

            // ------------------------------------------------------------------------------------

            server.startAsDaemon();

            waitForSlackAppConnection();

            // ------------------------------------------------------------------------------------

            String userId = slack.methods().authTest(r -> r.token(userToken)).getUserId();
            String botUserId = slack.methods().authTest(r -> r.token(botToken)).getUserId();

            // subteam_created
            UsergroupsCreateResponse creation = slack.methods(userToken).usergroupsCreate(r ->
                    r.name("test-group-" + System.currentTimeMillis()).description("test"));
            assertNull(creation.getError());
            String usergroupId = creation.getUsergroup().getId();
            createdUsergroupId = usergroupId;
            // subteam_members_changed
            UsergroupsUsersUpdateResponse memberUpdates =
                    slack.methods(userToken).usergroupsUsersUpdate(r ->
                            r.usergroup(usergroupId).users(Arrays.asList(userId)));
            assertNull(memberUpdates.getError());
            // subteam_self_added
            UsergroupsUsersUpdateResponse self =
                    slack.methods(userToken).usergroupsUsersUpdate(r ->
                            r.usergroup(usergroupId).users(Arrays.asList(userId, botUserId)));
            assertNull(self.getError());
            // subteam_self_removed
            UsergroupsUsersUpdateResponse selfRemoval =
                    slack.methods(userToken).usergroupsUsersUpdate(r ->
                            r.usergroup(usergroupId).users(Arrays.asList(botUserId)));
            assertNull(selfRemoval.getError());
            // subteam_updated

            // ------------------------------------------------------------------------------------

            long waitTime = 0;
            while (!state.isAllDone() && waitTime < 10_000L) {
                long sleepTime = 100L;
                Thread.sleep(sleepTime);
                waitTime += sleepTime;
            }
            assertTrue(state.toString(), state.isAllDone());

        } finally {
            server.stop();

            if (createdUsergroupId != null) {
                String id = createdUsergroupId;
                slack.methods(userToken).usergroupsDisable(r -> r.usergroup(id));
            }
        }
    }

    // ----------------------------
    // User Settings
    // ----------------------------
    // dnd_updated
    // dnd_updated_user

    @Data
    public static class DndTestState {
        private boolean dndUpdated;
        private boolean dndUpdatedUser;

        public boolean isAllDone() {
            // NOTE: As of Sep 3 2020, dnd_updated_user events are not sent to app
            // return dndUpdated && dndUpdatedUser;
            return dndUpdated;
        }
    }

    @Test
    public void dnd() throws Exception {

        App app = new App(appConfig);
        app.use(recorderMiddleware());

        String userToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_USER_TOKEN);

        TestSlackAppServer server = new TestSlackAppServer(app);
        DndTestState state = new DndTestState();

        try {
            // Subscribe to events on behalf of users

            // dnd_updated
            app.event(DndUpdatedEvent.class, (req, ctx) -> {
                state.setDndUpdated(true);
                return ctx.ack();
            });
            // dnd_updated_user
            app.event(DndUpdatedUserEvent.class, (req, ctx) -> {
                state.setDndUpdatedUser(true);
                return ctx.ack();
            });

            // ------------------------------------------------------------------------------------

            server.startAsDaemon();

            waitForSlackAppConnection();

            // ------------------------------------------------------------------------------------

            // dnd_updated
            // dnd_updated_user
            DndSetSnoozeResponse userSetSnooze = slack.methods(userToken).dndSetSnooze(r -> r.numMinutes(10));
            assertNull(userSetSnooze.getError());
            DndEndSnoozeResponse userEndSnooze = slack.methods(userToken).dndEndSnooze(r -> r);
            assertNull(userEndSnooze.getError());

            // ------------------------------------------------------------------------------------

            long waitTime = 0;
            while (!state.isAllDone() && waitTime < 10_000L) {
                long sleepTime = 100L;
                Thread.sleep(sleepTime);
                waitTime += sleepTime;
            }
            assertTrue(state.toString(), state.isAllDone());

        } finally {
            server.stop();
        }
    }

    // ----------------------------
    // Message with files
    // ----------------------------

    @Data
    public static class FileMessageState {
        private AtomicInteger fileShare = new AtomicInteger(0);
        private AtomicInteger messageChanged = new AtomicInteger(0);

        public boolean isAllDone() {
            return fileShare.get() == 1 && messageChanged.get() == 1;
        }
    }

    @Test
    public void messageWithFiles() throws Exception {

        App app = new App(appConfig);
        app.use(recorderMiddleware());

        String userToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_USER_TOKEN);
        String botToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_BOT_TOKEN);

        TestSlackAppServer server = new TestSlackAppServer(app);
        FileMessageState state = new FileMessageState();

        ConversationsCreateResponse publicChannel =
                slack.methods(botToken).conversationsCreate(r -> r.name("test-" + System.currentTimeMillis()).isPrivate(false));
        assertNull(publicChannel.getError());

        final String channelId = publicChannel.getChannel().getId();

        ConversationsJoinResponse joining = slack.methods(userToken).conversationsJoin(r -> r.channel(channelId));
        assertNull(joining.getError());

        try {
            app.event(MessageFileShareEvent.class, (req, ctx) -> {
                if (req.getEvent().getFiles() != null && req.getEvent().getFiles().size() > 0) {
                    state.fileShare.incrementAndGet();
                }
                return ctx.ack();
            });
            // FIXME: this is not called as of July 30, 2021
            app.event(MessageChangedEvent.class, (req, ctx) -> {
                if (req.getEvent().getMessage().getFiles() != null && req.getEvent().getMessage().getFiles().size() > 0
                        && req.getEvent().getPreviousMessage().getMessage().getFiles() != null && req.getEvent().getPreviousMessage().getMessage().getFiles().size() > 0) {
                    state.messageChanged.incrementAndGet();
                }
                return ctx.ack();
            });

            // ------------------------------------------------------------------------------------
            server.startAsDaemon();
            waitForSlackAppConnection();
            // ------------------------------------------------------------------------------------

            FilesUploadRequest uploadRequest = FilesUploadRequest.builder()
                    .title("test text")
                    .content("test test test")
                    .channels(Arrays.asList(channelId))
                    .initialComment("Here you are")
                    .build();
            FilesUploadResponse userResult = slack.methods(userToken).filesUpload(uploadRequest);
            assertNull(userResult.getError());

            String ts = userResult.getFile().getShares().getPublicChannels().get(channelId).get(0).getTs();
            ChatUpdateResponse updateResult = slack.methods(userToken).chatUpdate(r -> r
                    .channel(channelId)
                    .ts(ts)
                    .text("Here you are - let me know if you need other files as well"));
            assertNull(updateResult.getError());
            // ------------------------------------------------------------------------------------

            long waitTime = 0;
            while (!state.isAllDone() && waitTime < 5_000L) {
                long sleepTime = 100L;
                Thread.sleep(sleepTime);
                waitTime += sleepTime;
            }
            // FIXME: failing as of July 30, 2021
            assertTrue(state.toString(), state.isAllDone());

        } finally {
            server.stop();

            if (channelId != null) {
                slack.methods(botToken).conversationsArchive(r -> r.channel(channelId));
            }
        }
    }


    // ----------------------------
    // apps.event.authorizations.list
    // ----------------------------

    @Test
    public void appsEventAuthorizationsListCalls() throws Exception {

        App app = new App(appConfig);
        app.use(recorderMiddleware());

        String appLevelToken = System.getenv(Constants.SLACK_SDK_TEST_APP_TOKEN);
        String userToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_USER_TOKEN);
        String botToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_BOT_TOKEN);

        TestSlackAppServer server = new TestSlackAppServer(app);

        ConversationsCreateResponse publicChannel =
                slack.methods(botToken).conversationsCreate(r -> r
                        .name("test-" + System.currentTimeMillis()).isPrivate(false));
        assertNull(publicChannel.getError());

        final String channelId = publicChannel.getChannel().getId();
        ConversationsJoinResponse joining = slack.methods(userToken).conversationsJoin(r -> r.channel(channelId));
        assertNull(joining.getError());

        final AtomicBoolean called = new AtomicBoolean(false);

        try {
            app.event(MessageEvent.class, (req, ctx) -> {
                AppsEventAuthorizationsListResponse authorizations = recorderSlack.methods(appLevelToken).appsEventAuthorizationsList(r -> r
                        .eventContext(req.getEventContext())
                        .limit(10)
                );
                called.set(authorizations.isOk());
                return ctx.ack();
            });

            // ------------------------------------------------------------------------------------
            server.startAsDaemon();
            waitForSlackAppConnection();
            // ------------------------------------------------------------------------------------

            ChatPostMessageResponse chatPostMessageResponse =
                    slack.methods(userToken).chatPostMessage(r -> r.channel(channelId).text("Hi there!"));
            assertNull(chatPostMessageResponse.getError());

            // ------------------------------------------------------------------------------------

            long waitTime = 0;
            while (!called.get() && waitTime < 5_000L) {
                long sleepTime = 100L;
                Thread.sleep(sleepTime);
                waitTime += sleepTime;
            }
            assertTrue(called.get());

        } finally {
            server.stop();
            if (channelId != null) {
                slack.methods(botToken).conversationsArchive(r -> r.channel(channelId));
            }
        }
    }
}
