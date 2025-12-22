package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.ChatStreamHelper;
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

public class ChatStreamHelperTest {

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
        ChatStreamHelper stream = ChatStreamHelper.builder()
                .client(slack.methods(ValidToken))
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(100)
                .build();

        ChatAppendStreamResponse resp = stream.append("hello");
        assertThat(resp, is(nullValue()));
        assertThat(stream.getState(), is(ChatStreamHelper.State.STARTING));
        assertThat(stream.getStreamTs(), is(nullValue()));
        assertThat(stream.getBuffer().toString(), is("hello"));
    }

    @Test
    public void append_flushes_and_starts_stream_on_first_flush() throws Exception {
        ChatStreamHelper stream = ChatStreamHelper.builder()
                .client(slack.methods(ValidToken))
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(3)
                .build();

        ChatAppendStreamResponse resp = stream.append("hey"); // triggers flush
        assertThat(resp.isOk(), is(true));
        assertThat(stream.getState(), is(ChatStreamHelper.State.IN_PROGRESS));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
        assertThat(stream.getBuffer().toString(), is(""));
    }

    @Test
    public void append_flushes_with_appendStream_after_started() throws Exception {
        ChatStreamHelper stream = ChatStreamHelper.builder()
                .client(slack.methods(ValidToken))
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1)
                .build();

        // first flush uses chat.startStream
        ChatAppendStreamResponse first = stream.append("a");
        assertThat(first.isOk(), is(true));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
        assertThat(stream.getState(), is(ChatStreamHelper.State.IN_PROGRESS));

        // second flush uses chat.appendStream
        ChatAppendStreamResponse second = stream.append("b");
        assertThat(second.isOk(), is(true));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
        assertThat(stream.getState(), is(ChatStreamHelper.State.IN_PROGRESS));
    }

    @Test
    public void stop_starts_stream_if_needed_and_completes() throws Exception {
        ChatStreamHelper stream = ChatStreamHelper.builder()
                .client(slack.methods(ValidToken))
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000)
                .build();

        stream.append("hello"); // buffered only
        ChatStopStreamResponse stop = stream.stop();
        assertThat(stop.isOk(), is(true));
        assertThat(stream.getState(), is(ChatStreamHelper.State.COMPLETED));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
    }

    @Test(expected = SlackChatStreamException.class)
    public void append_throws_after_completed() throws Exception {
        ChatStreamHelper stream = ChatStreamHelper.builder()
                .client(slack.methods(ValidToken))
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000)
                .build();

        stream.stop();
        stream.append("nope");
    }
}


