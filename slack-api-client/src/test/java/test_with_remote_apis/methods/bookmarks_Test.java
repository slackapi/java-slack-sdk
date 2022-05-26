package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.bookmarks.BookmarksAddResponse;
import com.slack.api.methods.response.bookmarks.BookmarksEditResponse;
import com.slack.api.methods.response.bookmarks.BookmarksListResponse;
import com.slack.api.methods.response.bookmarks.BookmarksRemoveResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class bookmarks_Test {

    static String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("bookmarks.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void noArgs() throws IOException, SlackApiException {
        {
            BookmarksListResponse response = slack.methods(botToken).bookmarksList(req -> req);
            assertThat(response.getError(), is(notNullValue()));
        }
        {
            BookmarksAddResponse response = slack.methods(botToken).bookmarksAdd(req -> req);
            assertThat(response.getError(), is(notNullValue()));
        }
        {
            BookmarksEditResponse response = slack.methods(botToken).bookmarksEdit(req -> req);
            assertThat(response.getError(), is(notNullValue()));
        }
        {
            BookmarksRemoveResponse response = slack.methods(botToken).bookmarksRemove(req -> req);
            assertThat(response.getError(), is(notNullValue()));
        }
    }

    @Test
    public void successfulCalls() throws IOException, SlackApiException {
        ConversationsListResponse conversations = slack.methods(botToken).conversationsList(r -> r
                .excludeArchived(true));
        assertThat(conversations.getError(), is(nullValue()));
        String channelId = conversations.getChannels().get(0).getId();

        BookmarksListResponse bookmarks = slack.methods(botToken).bookmarksList(r -> r.channelId(channelId));
        assertThat(bookmarks.getError(), is(nullValue()));

        ChatPostMessageResponse newMessage = slack.methods(botToken).chatPostMessage(r -> r
                .channel(channelId)
                .text("A very important message!"));
        assertThat(newMessage.getError(), is(nullValue()));

        String permalink = slack.methods(botToken).chatGetPermalink(r -> r
                .channel(channelId)
                .messageTs(newMessage.getTs())
        ).getPermalink();

        BookmarksAddResponse linkBookmark = slack.methods(botToken).bookmarksAdd(req -> req
                .channelId(channelId)
                .title("test")
                .link(permalink)
                .type("link")
        );
        assertThat(linkBookmark.getError(), is(nullValue()));

        BookmarksEditResponse modification = slack.methods(botToken).bookmarksEdit(req -> req
                .channelId(channelId)
                .bookmarkId(linkBookmark.getBookmark().getId())
                .title("test")
                .link(permalink)
        );
        assertThat(modification.getError(), is(nullValue()));

        BookmarksRemoveResponse removal = slack.methods(botToken).bookmarksRemove(req -> req
                .channelId(channelId)
                .bookmarkId(modification.getBookmark().getId())
        );
        assertThat(removal.getError(), is(nullValue()));
    }
}