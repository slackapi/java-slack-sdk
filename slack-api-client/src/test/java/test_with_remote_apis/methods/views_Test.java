package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.methods.response.views.ViewsPublishResponse;
import com.slack.api.methods.response.views.ViewsPushResponse;
import com.slack.api.methods.response.views.ViewsUpdateResponse;
import com.slack.api.model.view.View;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class views_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    // we don't store the result by this API call
    // see also: test_locally.sample_json_generation.MethodsResponseDumpTest
    static Slack slack = Slack.getInstance();

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("views.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    /*
     * A view in Slack can only be opened in response to a user action such as a slash command or
     * button click (which now include trigger_ids in callbacks). A view.open request has to include
     * that same trigger_id in order to succeed. The views.* request must also be made within 3
     * seconds of the user action.  Therefore, only an 'invalid trigger' ID response can be tested.
     */

    @Test
    public void open() throws IOException, SlackApiException, ExecutionException, InterruptedException {
        View view = View.builder().id("FAKE_ID").build();
        {
            ViewsOpenResponse response = slack.methods().viewsOpen(r -> r
                    .token(botToken)
                    .triggerId("FAKE_TRIGGER_ID")
                    .view(view));
            assertThat(response.isOk(), is(false));
            assertThat(response.getError(), is("invalid_arguments"));
        }
        {
            ViewsOpenResponse response = slack.methodsAsync().viewsOpen(r -> r
                    .token(botToken)
                    .triggerId("FAKE_TRIGGER_ID")
                    .view(view))
                    .get();
            assertThat(response.isOk(), is(false));
            assertThat(response.getError(), is("invalid_arguments"));
        }
    }

    @Test
    public void push() throws IOException, SlackApiException, ExecutionException, InterruptedException {
        View view = View.builder().id("FAKE_ID").build();
        {
            ViewsPushResponse response = slack.methods().viewsPush(r -> r
                    .token(botToken)
                    .triggerId("FAKE_TRIGGER_ID")
                    .view(view));
            assertThat(response.isOk(), is(false));
            assertThat(response.getError(), is("invalid_arguments"));
        }
        {
            ViewsPushResponse response = slack.methodsAsync().viewsPush(r -> r
                    .token(botToken)
                    .triggerId("FAKE_TRIGGER_ID")
                    .view(view))
                    .get();
            assertThat(response.isOk(), is(false));
            assertThat(response.getError(), is("invalid_arguments"));
        }
    }

    @Test
    public void update() throws IOException, SlackApiException, ExecutionException, InterruptedException {
        View view = View.builder().id("FAKE_ID").build();
        {
            ViewsUpdateResponse response = slack.methods().viewsUpdate(r -> r
                    .token(botToken)
                    .externalId("FAKE_EXTERNAL_ID")
                    .hash("FAKE_HASH")
                    .viewId("FAKE_VIEW_ID")
                    .view(view));
            assertThat(response.isOk(), is(false));
            assertThat(response.getError(), is("invalid_arguments"));
        }
        {
            ViewsUpdateResponse response = slack.methodsAsync().viewsUpdate(r -> r
                    .token(botToken)
                    .externalId("FAKE_EXTERNAL_ID")
                    .hash("FAKE_HASH")
                    .viewId("FAKE_VIEW_ID")
                    .view(view))
                    .get();
            assertThat(response.isOk(), is(false));
            assertThat(response.getError(), is("invalid_arguments"));
        }
    }

    @Test
    public void publish() throws IOException, SlackApiException, ExecutionException, InterruptedException {
        View view = View.builder().id("FAKE_ID").build();
        {
            ViewsPublishResponse response = slack.methods().viewsPublish(r -> r
                    .token(botToken)
                    .userId("FAKE_USER_ID")
                    .view(view));
            assertThat(response.isOk(), is(false));
            // need to join the beta
            assertThat(response.getError(), is("invalid_arguments"));
        }
        {
            ViewsPublishResponse response = slack.methodsAsync().viewsPublish(r -> r
                    .token(botToken)
                    .userId("FAKE_USER_ID")
                    .view(view))
                    .get();
            assertThat(response.isOk(), is(false));
            // need to join the beta
            assertThat(response.getError(), is("invalid_arguments"));
        }
    }

}
