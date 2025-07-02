package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.files.FilesUploadResponse;
import com.slack.api.methods.response.stars.StarsAddResponse;
import com.slack.api.methods.response.stars.StarsListResponse;
import com.slack.api.methods.response.stars.StarsRemoveResponse;
import com.slack.api.model.Conversation;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class stars_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

//    @BeforeClass
//    public static void setUp() throws Exception {
//        SlackTestConfig.initializeRawJSONDataFiles("stars.*");
//    }
//
//    @AfterClass
//    public static void tearDown() throws InterruptedException {
//        SlackTestConfig.awaitCompletion(testConfig);
//    }

    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

    @Test
    @Ignore // https://docs.slack.dev/changelog/2023-07-its-later-already-for-stars-and-reminders
    public void list() throws IOException, SlackApiException {
        StarsListResponse response = slack.methods().starsList(r -> r.token(userToken));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getItems(), is(notNullValue()));
    }

    @Test
    @Ignore // https://docs.slack.dev/changelog/2023-07-its-later-already-for-stars-and-reminders
    public void list_async() throws Exception {
        StarsListResponse response = slack.methodsAsync().starsList(r -> r.token(userToken)).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getItems(), is(notNullValue()));
    }

    @Test
    @Ignore // https://docs.slack.dev/changelog/2023-07-its-later-already-for-stars-and-reminders
    public void add() throws IOException, SlackApiException {
        List<Conversation> channels = slack.methods().conversationsList(r -> r.token(userToken)).getChannels();
        List<String> channelIds = new ArrayList<>();
        for (Conversation c : channels) {
            if (c.getName().equals("random")) {
                channelIds.add(c.getId());
                break;
            }
        }

        File file = new File("src/test/resources/sample.txt");
        com.slack.api.model.File fileObj;
        {
            FilesUploadResponse response = slack.methods().filesUpload(r -> r
                    .token(userToken)
                    .channels(channelIds)
                    .file(file)
                    .filename("sample.txt")
                    .initialComment("initial comment")
                    .title("file title"));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            fileObj = response.getFile();
        }

        {
            // To remove the star before calling stars.add
            slack.methods().starsRemove(r -> r
                    .token(userToken)
                    .channel(channelIds.get(0))
                    .file(fileObj.getId()));

            StarsAddResponse response = slack.methods().starsAdd(r -> r
                    .token(userToken)
                    .channel(channelIds.get(0))
                    .file(fileObj.getId()));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
        {
            StarsRemoveResponse response = slack.methods().starsRemove(r -> r
                    .token(userToken)
                    .channel(channelIds.get(0))
                    .file(fileObj.getId()));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

//        {
//            // as of August 2018, File object no longer contains initialComment.
//            if (fileObj.getInitialComment() != null) {
//                StarsAddResponse response = slack.methods().starsAdd(r -> r
//                        .token(token)
//                        .channel(channelIds.get(0))
//                        .fileComment(fileObj.getInitialComment().getId()));
//                assertThat(response.getError(), is(nullValue()));
//                assertThat(response.isOk(), is(true));
//            }
//        }

    }

}
