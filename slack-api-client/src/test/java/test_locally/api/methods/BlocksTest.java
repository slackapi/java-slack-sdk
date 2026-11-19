package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.request.blocks.BlocksValidateRequest;
import com.slack.api.methods.response.blocks.BlocksValidateResponse;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.view.View;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;
import java.util.List;

import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.view.Views.view;
import static com.slack.api.model.view.Views.viewTitle;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BlocksTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    private static final List<LayoutBlock> BLOCKS = Arrays.asList(
            section(s -> s.text(plainText("Hello world"))));
    private static final String BLOCKS_AS_STRING =
            "[{\"type\":\"section\",\"text\":{\"type\":\"plain_text\",\"text\":\"Hello world\"}}]";

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void validate() throws Exception {
        BlocksValidateResponse response = slack.methods().blocksValidate(r -> r.blocks(BLOCKS));
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(notNullValue()));
        BlocksValidateResponse.Error error = response.getErrors().get(0);
        assertThat(error.getConstraint(), is(notNullValue()));
    }

    @Test
    public void validate_async() throws Exception {
        BlocksValidateResponse response = slack.methodsAsync()
                .blocksValidate(r -> r.blocksAsString(BLOCKS_AS_STRING)).get();
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(notNullValue()));
        BlocksValidateResponse.Error error = response.getErrors().get(0);
        assertThat(error.getConstraint(), is(notNullValue()));
    }

    @Test
    public void validate_message() throws Exception {
        BlocksValidateRequest.MessagePayload message = BlocksValidateRequest.MessagePayload.builder()
                .blocks(BLOCKS)
                .build();
        BlocksValidateResponse response = slack.methods().blocksValidate(r -> r.message(message));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void validate_view() throws Exception {
        View modal = view(v -> v
                .type("modal")
                .title(viewTitle(t -> t.type("plain_text").text("Title")))
                .blocks(BLOCKS));
        BlocksValidateResponse response = slack.methods().blocksValidate(r -> r.view(modal));
        assertThat(response.isOk(), is(true));
    }
}
