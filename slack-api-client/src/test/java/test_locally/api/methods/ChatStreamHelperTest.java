package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.ChatStreamHelper;
import com.slack.api.methods.SlackChatStreamException;
import com.slack.api.methods.response.chat.ChatAppendStreamResponse;
import com.slack.api.methods.response.chat.ChatStopStreamResponse;
import com.slack.api.model.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Collections;

import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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
        ChatStreamHelper stream = slack.methods(ValidToken).chatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(100));
    
        ChatAppendStreamResponse resp = stream.append("hello");
        assertThat(resp, is(nullValue()));
        assertThat(stream.getState(), is(ChatStreamHelper.State.STARTING));
        assertThat(stream.getStreamTs(), is(nullValue()));
        assertThat(stream.getBuffer().toString(), is("hello"));
    }

    @Test
    public void append_flushes_and_starts_stream_on_first_flush() throws Exception {
        ChatStreamHelper stream = slack.methods(ValidToken).chatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(3));

        ChatAppendStreamResponse resp = stream.append("hey"); // triggers flush
        assertThat(resp.isOk(), is(true));
        assertThat(stream.getState(), is(ChatStreamHelper.State.IN_PROGRESS));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
        assertThat(stream.getBuffer().toString(), is(""));
    }

    @Test
    public void append_flushes_with_appendStream_after_started() throws Exception {
        ChatStreamHelper stream = slack.methods(ValidToken).chatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1));

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
        ChatStreamHelper stream = slack.methods(ValidToken).chatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000));

        stream.append("hello"); // buffered only
        ChatStopStreamResponse stop = stream.stop();
        assertThat(stop.isOk(), is(true));
        assertThat(stream.getState(), is(ChatStreamHelper.State.COMPLETED));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
    }

    @Test(expected = SlackChatStreamException.class)
    public void append_throws_after_completed() throws Exception {
        ChatStreamHelper stream = slack.methods(ValidToken).chatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000));

        stream.stop();
        stream.append("nope");
    }

    @Test(expected = SlackChatStreamException.class)
    public void stop_throws_after_completed() throws Exception {
        ChatStreamHelper stream = slack.methods(ValidToken).chatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000));

        stream.stop();
        stream.stop(); // second stop should throw
    }

    @Test
    public void stop_with_additional_markdown_text() throws Exception {
        ChatStreamHelper stream = slack.methods(ValidToken).chatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000));

        stream.append("hello ");
        ChatStopStreamResponse stop = stream.stop("world!");
        assertThat(stop.isOk(), is(true));
        assertThat(stream.getState(), is(ChatStreamHelper.State.COMPLETED));
        assertThat(stream.getBuffer().toString(), is("hello world!"));
    }

    @Test
    public void stop_with_blocks_and_metadata() throws Exception {
        ChatStreamHelper stream = slack.methods(ValidToken).chatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000));

        Message.Metadata metadata = new Message.Metadata();
        metadata.setEventType("test_event");
        metadata.setEventPayload(Collections.singletonMap("key", "value"));

        ChatStopStreamResponse stop = stream.stop(
                "final text",
                Collections.singletonList(section(s -> s.text(plainText("Block text")))),
                metadata
        );
        assertThat(stop.isOk(), is(true));
        assertThat(stream.getState(), is(ChatStreamHelper.State.COMPLETED));
    }

    @Test
    public void stop_after_stream_already_started() throws Exception {
        ChatStreamHelper stream = slack.methods(ValidToken).chatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1)); // force immediate flush

        // Start the stream via append
        ChatAppendStreamResponse appendResp = stream.append("a");
        assertThat(appendResp.isOk(), is(true));
        assertThat(stream.getState(), is(ChatStreamHelper.State.IN_PROGRESS));
        assertThat(stream.getStreamTs(), is(notNullValue()));

        // Now stop - should not call startStream again
        ChatStopStreamResponse stop = stream.stop();
        assertThat(stop.isOk(), is(true));
        assertThat(stream.getState(), is(ChatStreamHelper.State.COMPLETED));
    }

    @Test
    public void default_buffer_size_is_100() {
        ChatStreamHelper stream = slack.methods(ValidToken).chatStreamHelper(req -> req
                .channel("C123"));

        assertThat(stream.getBufferSize(), is(100));
    }

    @Test
    public void multiple_appends_accumulate_in_buffer() throws Exception {
        ChatStreamHelper stream = slack.methods(ValidToken).chatStreamHelper(req -> req
                .channel("C123")
                .bufferSize(100));

        stream.append("hello");
        stream.append(" ");
        stream.append("world");

        assertThat(stream.getBuffer().toString(), is("hello world"));
        assertThat(stream.getState(), is(ChatStreamHelper.State.STARTING));
    }
}


