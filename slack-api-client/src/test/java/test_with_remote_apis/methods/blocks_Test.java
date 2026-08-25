package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.response.blocks.BlocksValidateResponse;
import config.Constants;
import config.SlackTestConfig;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class blocks_Test {

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void validate_wellFormed() throws Exception {
        String validBlocks = "[{\"type\":\"section\",\"text\":{\"type\":\"mrkdwn\",\"text\":\"Hello\"}}]";
        BlocksValidateResponse response = slack.methods(botToken).blocksValidate(r -> r.blocks(validBlocks));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors() == null || response.getErrors().isEmpty(), is(true));
    }

    @Test
    public void validate_malformed() throws Exception {
        // A section whose text carries an unsupported "type" fails validation.
        String invalidBlocks = "[{\"type\":\"section\",\"text\":{\"type\":\"invalid\",\"text\":\"Hello\"}}]";
        BlocksValidateResponse response = slack.methods(botToken).blocksValidate(r -> r.blocks(invalidBlocks));
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is("invalid_blocks"));
        List<BlocksValidateResponse.Error> errors = response.getErrors();
        assertThat(errors, is(notNullValue()));
        assertThat(errors.isEmpty(), is(false));
        BlocksValidateResponse.Error first = errors.get(0);
        assertThat(first.getPointer(), is(notNullValue()));
        assertThat(first.getCode(), is(notNullValue()));
        assertThat(first.getMessage(), is(notNullValue()));
    }
}
