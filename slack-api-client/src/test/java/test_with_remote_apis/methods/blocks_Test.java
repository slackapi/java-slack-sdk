package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.response.blocks.BlocksValidateResponse;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.LayoutBlock;
import config.SlackTestConfig;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class blocks_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void validate_wellFormed() throws Exception {
        BlocksValidateResponse response = slack.methods().blocksValidate(r -> r
                .blocks(Arrays.asList(section(s -> s.text(markdownText("Hello"))))));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors() == null || response.getErrors().isEmpty(), is(true));
    }

    @Test
    public void validate_malformed() throws Exception {
        String invalidBlocks = "[{\"type\":\"section\",\"text\":{\"type\":\"invalid\",\"text\":\"Hello\"}}]";
        BlocksValidateResponse response = slack.methods().blocksValidate(r -> r.blocksAsString(invalidBlocks));
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

    @Test
    public void validate_numericConstraint() throws Exception {
        // Exceeding the block limit returns a max_items constraint whose expected/got are numbers,
        // not the enum case's string array — exercising the polymorphic constraint fields.
        List<LayoutBlock> tooMany = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            tooMany.add(DividerBlock.builder().build());
        }
        BlocksValidateResponse response = slack.methods().blocksValidate(r -> r.blocks(tooMany));
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is("invalid_blocks"));
        BlocksValidateResponse.Error.Constraint constraint = response.getErrors().get(0).getConstraint();
        assertThat(constraint, is(notNullValue()));
        assertThat(constraint.getExpected().getAsInt(), is(50));
        assertThat(constraint.getGot().getAsInt(), is(60));
    }
}
