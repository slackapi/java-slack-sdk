package test_with_remote_apis;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.canvases.sections.CanvasesSectionsLookupRequest;
import com.slack.api.methods.response.bookmarks.BookmarksAddResponse;
import com.slack.api.methods.response.bookmarks.BookmarksEditResponse;
import com.slack.api.methods.response.bookmarks.BookmarksListResponse;
import com.slack.api.methods.response.bookmarks.BookmarksRemoveResponse;
import com.slack.api.methods.response.bots.BotsInfoResponse;
import com.slack.api.methods.response.canvases.CanvasesCreateResponse;
import com.slack.api.methods.response.canvases.CanvasesDeleteResponse;
import com.slack.api.methods.response.canvases.CanvasesEditResponse;
import com.slack.api.methods.response.canvases.access.CanvasesAccessDeleteResponse;
import com.slack.api.methods.response.canvases.access.CanvasesAccessSetResponse;
import com.slack.api.methods.response.canvases.sections.CanvasesSectionsLookupResponse;
import com.slack.api.methods.response.chat.ChatDeleteResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.chat.ChatUpdateResponse;
import com.slack.api.methods.response.conversations.ConversationsHistoryResponse;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.methods.response.conversations.ConversationsRepliesResponse;
import com.slack.api.methods.response.files.FilesInfoResponse;
import com.slack.api.methods.response.files.FilesUploadV2Response;
import com.slack.api.methods.response.team.TeamInfoResponse;
import com.slack.api.methods.response.team.TeamPreferencesListResponse;
import com.slack.api.methods.response.team.profile.TeamProfileGetResponse;
import com.slack.api.methods.response.users.UsersListResponse;
import com.slack.api.methods.response.users.profile.UsersProfileGetResponse;
import com.slack.api.model.Conversation;
import com.slack.api.model.User;
import com.slack.api.model.canvas.*;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

/**
 * The purpose of this test suite is to detect important property updates in API responses.
 * In particular, the following elements are often quietly added to production API responses,
 * and the lack of these properties can affect developers so much:
 * - Block Kit components
 * - File object metadata
 * - User info details
 * Therefore, regularly running these tests will help quickly identify any changes.
 * <p>
 * To execute this test suite, you need to create a sandbox environment
 * and install your custom app with appropriate bot scopes.
 * <p>
 * For future updates, you can reuse the code under `test_with_remote_apis.methods` as needed.
 * It's okay if it's not perfect initially; you can gradually enhance your test assets over time.
 */
@Slf4j
public class MinimumPropertyDetectionTest {

    // Setting this env variable is required;
    // If you want to go with a different env variable name, feel free to go with it
    static String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    // This TestConfig enables this test execution to detect missing properties in Java code
    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());
    // Use this Web API client for testing
    static MethodsClient client = slack.methods(botToken);
    // You can use this async client to avoid failing with a "ratelimited" error
    static AsyncMethodsClient asyncClient = slack.methodsAsync(botToken);

    @BeforeClass
    public static void setUp() throws Exception {
        // Usually these invocations do nothing; check the code to learn what it does
        SlackTestConfig.initializeRawJSONDataFiles("bookmarks.*");
        SlackTestConfig.initializeRawJSONDataFiles("bots.*");
        SlackTestConfig.initializeRawJSONDataFiles("canvases.*");
        SlackTestConfig.initializeRawJSONDataFiles("chat.*");
        SlackTestConfig.initializeRawJSONDataFiles("conversations.*");
        SlackTestConfig.initializeRawJSONDataFiles("files.*");
        SlackTestConfig.initializeRawJSONDataFiles("team.*");
        SlackTestConfig.initializeRawJSONDataFiles("usergroups.*");
        SlackTestConfig.initializeRawJSONDataFiles("users.*");
    }

    @Before
    public void beforeEachTest() throws Exception {
        // Changing this method for optimizing the test performance is totally fine
        // The fastest approach would be to have the channel ID as an env variable
        loadTestChannelId();
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        // This part finalizes the generated JSON files; there may be some rooms for improvements
        SlackTestConfig.awaitCompletion(testConfig);
    }

    // If #random does not work for your testing workspace, replacing this name with a different one is totally fine
    private static final String TEST_CHANNEL_NAME = "random";
    private String testChannelId = null;

    void loadTestChannelId() throws IOException, SlackApiException {
        if (testChannelId == null) {
            ConversationsListResponse channels = slack.methods().conversationsList(r -> r
                    .token(botToken)
                    .excludeArchived(true)
                    .limit(100)
            );
            assertThat(channels.getError(), is(nullValue()));

            for (Conversation channel : channels.getChannels()) {
                if (channel.getName().equals(TEST_CHANNEL_NAME)) {
                    testChannelId = channel.getId();
                    break;
                }
            }
        }
    }

    // -----------------------------------------------------------------------------------------
    // When you run the following test methods and the returned API response has unknown properties,
    // The test fails with an exception; check the error message to know what's missing in current Java code
    // -----------------------------------------------------------------------------------------

    @Test
    public void bookmarks() throws Exception {
        BookmarksListResponse bookmarks = client.bookmarksList(r -> r
                .channelId(testChannelId)
        );
        assertThat(bookmarks.getError(), is(nullValue()));

        ChatPostMessageResponse message = client.chatPostMessage(r -> r
                .channel(testChannelId)
                .text("A very important message!"));
        assertThat(message.getError(), is(nullValue()));

        String permalink = client.chatGetPermalink(r -> r
                .channel(testChannelId)
                .messageTs(message.getTs())
        ).getPermalink();

        BookmarksAddResponse creation = client.bookmarksAdd(req -> req
                .channelId(testChannelId)
                .title("test")
                .link(permalink)
                .type("link")
        );
        assertThat(creation.getError(), is(nullValue()));

        BookmarksEditResponse modification = client.bookmarksEdit(req -> req
                .channelId(testChannelId)
                .bookmarkId(creation.getBookmark().getId())
                .title("test")
                .link(permalink)
        );
        assertThat(modification.getError(), is(nullValue()));

        BookmarksRemoveResponse removal = client.bookmarksRemove(req -> req
                .channelId(testChannelId)
                .bookmarkId(modification.getBookmark().getId())
        );
        assertThat(removal.getError(), is(nullValue()));
    }

    @Test
    public void bots() throws Exception {
        User botUser = null;
        String cursor = null;
        while (botUser == null && (cursor == null || !cursor.isEmpty())) {
            // using async client to prevent failing due to a rate limited error
            UsersListResponse response = asyncClient.usersList(req -> req).get();
            for (User u : response.getMembers()) {
                if (u.isBot() && !"USLACKBOT".equals(u.getId())) {
                    botUser = u;
                    break;
                }
            }
            if (response.getResponseMetadata() != null) {
                cursor = response.getResponseMetadata().getNextCursor();
            }
        }
        assertThat(botUser, is(notNullValue()));

        String botId = botUser.getProfile().getBotId();
        BotsInfoResponse response = client.botsInfo(req -> req.bot(botId));
        assertThat(response.getError(), is(nullValue()));
    }

    @Test
    public void canvases() throws Exception {
        CanvasesCreateResponse creation = client.canvasesCreate(r -> r
                .title("My canvas " + System.currentTimeMillis())
                .documentContent(CanvasDocumentContent.builder().markdown(
                        "# Standalone canvas document\n" +
                                "\n" +
                                "---\n" +
                                "## Before\n" +
                                "**foo** _bar_\n" +
                                "hey hey\n" +
                                "\n").build())
        );
        assertThat(creation.getError(), is(nullValue()));

        String canvasId = creation.getCanvasId();
        try {
            List<String> userIds = Collections.singletonList(client.authTest(r -> r).getUserId());
            CanvasesSectionsLookupResponse lookupResult = client.canvasesSectionsLookup(r -> r
                    .canvasId(canvasId)
                    .criteria(CanvasesSectionsLookupRequest.Criteria.builder()
                            .sectionTypes(Collections.singletonList(CanvasDocumentSectionType.H2))
                            .containsText("Before")
                            .build()
                    )
            );
            assertThat(lookupResult.getError(), is(nullValue()));

            String sectionId = lookupResult.getSections().get(0).getId();
            CanvasesEditResponse edit = client.canvasesEdit(r -> r
                    .canvasId(canvasId)
                    .changes(Collections.singletonList(CanvasDocumentChange.builder()
                            .sectionId(sectionId)
                            .operation(CanvasEditOperation.REPLACE)
                            .documentContent(CanvasDocumentContent.builder().markdown("## After").build())
                            .build()
                    ))
            );
            assertThat(edit.getError(), is(nullValue()));

            FilesInfoResponse details = client.filesInfo(r -> r.file(canvasId));
            assertThat(details.getError(), is(nullValue()));

            CanvasesAccessSetResponse set = client.canvasesAccessSet(r -> r
                    .canvasId(canvasId)
                    .accessLevel(CanvasDocumentAccessLevel.WRITE)
                    .userIds(userIds)
            );
            assertThat(set.getError(), is(nullValue()));
            CanvasesAccessDeleteResponse delete = client.canvasesAccessDelete(r -> r
                    .canvasId(canvasId)
                    .userIds(userIds)
            );
            assertThat(delete.getError(), is(nullValue()));

        } finally {
            CanvasesDeleteResponse deletion = client.canvasesDelete(r -> r.canvasId(canvasId));
            assertThat(deletion.getError(), is(nullValue()));
        }
    }

    @Test
    public void chat() throws Exception {
        String text = "This is a _test_ message posted by `test_with_remote_apis.MinimumPropertyDetectionTest`";
        ChatPostMessageResponse newMessage = client.chatPostMessage(req -> req
                .channel(testChannelId)
                .text(text)
        );
        assertThat(newMessage.getError(), is(nullValue()));
        assertThat(newMessage.getMessage().getText(), is(text));

        String messageTs = newMessage.getTs();

        ConversationsHistoryResponse history = client.conversationsHistory(req -> req
                .channel(testChannelId)
                .latest(messageTs)
                .inclusive(true)
        );
        assertThat(history.getError(), is(nullValue()));

        ConversationsRepliesResponse replies = client.conversationsReplies(req -> req
                .channel(testChannelId)
                .ts(messageTs)
                .inclusive(true)
        );
        assertThat(replies.getError(), is(nullValue()));

        ChatUpdateResponse modification = client.chatUpdate(req -> req
                .channel(testChannelId)
                .ts(messageTs)
                .text("**EDIT:** " + text)
        );
        assertThat(modification.getError(), is(nullValue()));

        ChatDeleteResponse deletion = client.chatDelete(req -> req.channel(testChannelId).ts(messageTs));
        assertThat(deletion.getError(), is(nullValue()));
    }

    @Test
    public void files() throws Exception {
        File file = new File("src/test/resources/sample_long.txt");
        FilesUploadV2Response upload = client.filesUploadV2(r -> r
                .file(file)
                .channel(testChannelId)
                .initialComment("initial comment")
                .filename("sample.txt")
                .title("file title")
        );
        assertThat(upload.getError(), is(nullValue()));
        assertThat(upload.isOk(), is(true));
        assertThat(upload.getFile().getTitle(), is("file title"));
        assertThat(upload.getFile().getName(), is("sample.txt"));

        com.slack.api.model.File fileObj = null;
        while (fileObj == null || fileObj.getShares() == null || fileObj.getShares().getPublicChannels() == null) {
            fileObj = client.filesInfo(r -> r.file(upload.getFile().getId())).getFile();
            Thread.sleep(1_000L);
        }
        assertThat(fileObj.getTitle(), is("file title"));
        assertThat(fileObj.getName(), is("sample.txt"));

        String messageTs = fileObj.getShares().getPublicChannels().get(testChannelId).get(0).getTs();
        ChatDeleteResponse response = slack.methods(botToken).chatDelete(r -> r
                .channel(testChannelId)
                .ts(messageTs)
        );
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }


    @Test
    public void team() throws Exception {
        TeamInfoResponse team = client.teamInfo(r -> r);
        assertThat(team.getError(), is(nullValue()));

        String teamId = team.getTeam().getId();

        TeamProfileGetResponse profile = client.teamProfileGet(r -> r.teamId(teamId));
        assertThat(profile.getError(), is(nullValue()));

        TeamPreferencesListResponse preferences = client.teamPreferencesList(r -> r);
        assertThat(preferences.getError(), is(nullValue()));
    }

    @Test
    public void users_profile() throws Exception {
        User humanUser = null;
        // Using async client to avoid an exception due to rate limited errors
        for (User user : asyncClient.usersList(r -> r).get().getMembers()) {
            if (!user.isBot() && !user.isAppUser() && !user.isStranger() && !user.isDeleted()) {
                humanUser = user;
                break;
            }
        }
        String humanUserId = humanUser.getId();
        UsersProfileGetResponse profileGet = client.usersProfileGet(r -> r.user(humanUserId));
        assertThat(profileGet.getError(), is(nullValue()));
    }

    @Test
    public void users() throws Exception {
        // Scanning all users is useful to detect optional property existence
        List<String> userIds = new ArrayList<>();
        String nextCursor = null;
        while (nextCursor == null || !nextCursor.isEmpty()) {
            // Using async client to avoid an exception due to rate limited errors
            UsersListResponse response = asyncClient.usersList(r -> r
                    .includeLocale(true)
                    .limit(3000)
            ).get();
            nextCursor = response.getResponseMetadata().getNextCursor();
            userIds.addAll(response.getMembers().stream().map(User::getId).collect(toList()));
        }
        assertThat(userIds.size(), is(greaterThan(0)));
    }
}
