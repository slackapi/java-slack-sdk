package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.response.admin.emoji.*;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApi_emoji_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);

    static AsyncMethodsClient methodsAsync = slack.methodsAsync(orgAdminUserToken);

    @Test
    public void emoji() throws Exception {
        if (orgAdminUserToken != null) {
            CompletableFuture<AdminEmojiListResponse> list = methodsAsync.adminEmojiList(r -> r.limit(100));
            AdminEmojiListResponse emoji = list.get();
            assertThat(emoji.getEmoji().isEmpty(), is(false));

            Thread.sleep(3000);

            CompletableFuture<AdminEmojiAddResponse> creationError = methodsAsync.adminEmojiAdd(r -> r.name("test"));
            assertThat(creationError.get().getError(), is("invalid_arguments"));

            Thread.sleep(10000);

            String name = "java-" + System.currentTimeMillis();
            String url = "https://emoji.slack-edge.com/T03E94MJU/java/624937af2b22523e.png";

            CompletableFuture<AdminEmojiAddResponse> creation = methodsAsync.adminEmojiAdd(r -> r.name(name).url(url));
            assertThat(creation.get().getError(), is(nullValue()));

            CompletableFuture<AdminEmojiAddAliasResponse> aliasCreationError = methodsAsync.adminEmojiAddAlias(r -> r.name(name));
            assertThat(aliasCreationError.get().getError(), is("invalid_arguments"));

            Thread.sleep(10000);

            CompletableFuture<AdminEmojiAddAliasResponse> aliasCreation = methodsAsync.adminEmojiAddAlias(r -> r
                    .name(name + "-alias").aliasFor(name));
            assertThat(aliasCreation.get().getError(), is(nullValue()));

            CompletableFuture<AdminEmojiRenameResponse> renamingError = methodsAsync.adminEmojiRename(r -> r.name(name));
            assertThat(renamingError.get().getError(), is("invalid_arguments"));

            Thread.sleep(10000);

            CompletableFuture<AdminEmojiRenameResponse> renaming = methodsAsync.adminEmojiRename(r -> r.name(name).newName(name + "-2"));
            assertThat(renaming.get().getError(), is(nullValue()));

            CompletableFuture<AdminEmojiRemoveResponse> removalError = methodsAsync.adminEmojiRemove(r -> r);
            assertThat(removalError.get().getError(), is("invalid_arguments"));

            Thread.sleep(10000);

            CompletableFuture<AdminEmojiRemoveResponse> removal = methodsAsync.adminEmojiRemove(r -> r.name(name + "-2"));
            assertThat(removal.get().getError(), is(nullValue()));
        }
    }

}
