package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.response.reminders.*;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class reminders_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

    @Test
    public void test() throws Exception {
        RemindersListResponse list = slack.methods().remindersList(r -> r.token(userToken));
        assertThat(list.getError(), is(nullValue()));

        RemindersAddResponse addResponse = slack.methods().remindersAdd(r -> r
                .token(userToken)
                .text("Don't forget it!")
                .time("10"));
        assertThat(addResponse.getError(), is(nullValue()));
        assertThat(addResponse.isOk(), is(true));

        String reminderId = addResponse.getReminder().getId();

        RemindersInfoResponse infoResponse = slack.methods().remindersInfo(r -> r
                .token(userToken)
                .reminder(reminderId));
        assertThat(infoResponse.getError(), is(nullValue()));
        assertThat(infoResponse.isOk(), is(true));

        RemindersCompleteResponse completeResponse = slack.methods().remindersComplete(r -> r
                .token(userToken)
                .reminder(reminderId));
        assertThat(completeResponse.getError(), is(nullValue()));
        assertThat(completeResponse.isOk(), is(true));

        RemindersDeleteResponse deleteResponse = slack.methods().remindersDelete(r -> r
                .token(userToken)
                .reminder(reminderId));
        assertThat(deleteResponse.getError(), is(nullValue()));
        assertThat(deleteResponse.isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        RemindersAddResponse addResponse = slack.methodsAsync().remindersAdd(r -> r
                .token(userToken)
                .text("Don't forget it!")
                .time("10"))
                .get();
        assertThat(addResponse.getError(), is(nullValue()));
        assertThat(addResponse.isOk(), is(true));

        String reminderId = addResponse.getReminder().getId();

        RemindersInfoResponse infoResponse = slack.methodsAsync().remindersInfo(r -> r
                .token(userToken)
                .reminder(reminderId))
                .get();
        assertThat(infoResponse.getError(), is(nullValue()));
        assertThat(infoResponse.isOk(), is(true));

        RemindersCompleteResponse completeResponse = slack.methodsAsync().remindersComplete(r -> r
                .token(userToken)
                .reminder(reminderId))
                .get();
        assertThat(completeResponse.getError(), is(nullValue()));
        assertThat(completeResponse.isOk(), is(true));

        RemindersDeleteResponse deleteResponse = slack.methodsAsync().remindersDelete(r -> r
                .token(userToken)
                .reminder(reminderId))
                .get();
        assertThat(deleteResponse.getError(), is(nullValue()));
        assertThat(deleteResponse.isOk(), is(true));
    }

}
