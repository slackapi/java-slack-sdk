package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.response.blocks.BlocksValidateResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BlocksTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    private static final String BLOCKS =
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
        BlocksValidateResponse response = slack.methodsAsync().blocksValidate(r -> r.blocks(BLOCKS)).get();
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(notNullValue()));
        BlocksValidateResponse.Error error = response.getErrors().get(0);
        assertThat(error.getConstraint(), is(notNullValue()));
    }
}
