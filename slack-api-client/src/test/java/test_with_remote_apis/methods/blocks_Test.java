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
        // A well-formed blocks payload validates cleanly: ok=true, no top-level error, and an
        // empty (or absent) errors[]. See the "Typical success response" in the method reference:
        // https://docs.slack.dev/reference/methods/blocks.validate#response
        String validBlocks = "[{\"type\":\"section\",\"text\":{\"type\":\"mrkdwn\",\"text\":\"Hello\"}}]";
        BlocksValidateResponse response = slack.methods(botToken).blocksValidate(r -> r.blocks(validBlocks));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors() == null || response.getErrors().isEmpty(), is(true));
    }

    @Test
    public void validate_malformed() throws Exception {
        // A malformed blocks payload (a section with neither text nor fields) surfaces validation
        // feedback. This test records the live contract rather than asserting a fixed shape:
        // blocks.validate may answer with ok=false and error="invalid_blocks" plus a populated
        // errors[], or (defensively) ok=true with errors[]. Each Error mirrors the documented
        // contract — pointer / code / message / constraint — where constraint is a structured
        // object (e.g. {"type":"enum","expected":["plain_text","mrkdwn"]}), NOT a flat string.
        // https://docs.slack.dev/reference/methods/blocks.validate#validation-errors
        String invalidBlocks = "[{\"type\":\"section\"}]";
        BlocksValidateResponse response = slack.methods(botToken).blocksValidate(r -> r.blocks(invalidBlocks));
        List<BlocksValidateResponse.Error> errors = response.getErrors();
        if (!response.isOk()) {
            // Failed validation reports "invalid_blocks" and enumerates each issue in errors[].
            assertThat(response.getError(), is("invalid_blocks"));
        }
        // Whether ok is true or false, a malformed payload should enumerate at least one issue.
        assertThat(errors, is(notNullValue()));
        assertThat(errors.isEmpty(), is(false));
        BlocksValidateResponse.Error first = errors.get(0);
        // The three always-present fields of the structured validation feedback contract.
        assertThat(first.getPointer(), is(notNullValue()));
        assertThat(first.getCode(), is(notNullValue()));
        assertThat(first.getMessage(), is(notNullValue()));
    }
}
