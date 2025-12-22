package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.AsyncChatStreamHelper;
import com.slack.api.methods.SlackChatStreamException;
import com.slack.api.methods.response.chat.ChatAppendStreamResponse;
import com.slack.api.methods.response.chat.ChatStopStreamResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class AsyncChatStreamHelperTest {

    private final MockSlackApiServer server = new MockSlackApiServer();
    private final SlackConfig config = new SlackConfig();
    private final Slack slack = Slack.getInstance(config);

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
    public void append_buffers_when_under_bufferSize() throws Exception {
        AsyncChatStreamHelper stream = AsyncChatStreamHelper.builder()
                .client(slack.methodsAsync(ValidToken))
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(100)
                .build();

        ChatAppendStreamResponse resp = stream.append("hello").get();
        assertThat(resp, is(nullValue()));
        assertThat(stream.getState(), is(AsyncChatStreamHelper.State.STARTING));
        assertThat(stream.getStreamTs(), is(nullValue()));
        assertThat(stream.getBuffer().toString(), is("hello"));
    }

    @Test
    public void append_flushes_and_starts_stream_on_first_flush() throws Exception {
        AsyncChatStreamHelper stream = AsyncChatStreamHelper.builder()
                .client(slack.methodsAsync(ValidToken))
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(3)
                .build();

        ChatAppendStreamResponse resp = stream.append("hey").get(); // triggers flush
        assertThat(resp.isOk(), is(true));
        assertThat(stream.getState(), is(AsyncChatStreamHelper.State.IN_PROGRESS));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
        assertThat(stream.getBuffer().toString(), is(""));
    }

    @Test
    public void stop_completes() throws Exception {
        AsyncChatStreamHelper stream = AsyncChatStreamHelper.builder()
                .client(slack.methodsAsync(ValidToken))
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000)
                .build();

        stream.append("hello").get(); // buffered only
        ChatStopStreamResponse stop = stream.stop().get();
        assertThat(stop.isOk(), is(true));
        assertThat(stream.getState(), is(AsyncChatStreamHelper.State.COMPLETED));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
    }

    @Test(expected = SlackChatStreamException.class)
    public void append_throws_after_completed() throws Throwable {
        AsyncChatStreamHelper stream = AsyncChatStreamHelper.builder()
                .client(slack.methodsAsync(ValidToken))
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000)
                .build();

        stream.stop().get();
        try {
            stream.append("nope").get();
        } catch (Exception e) {
            // unwrap ExecutionException / CompletionException
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            throw cause;
        }
    }
}


