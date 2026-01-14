package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.AsyncChatStreamHelper;
import com.slack.api.methods.SlackChatStreamException;
import com.slack.api.methods.response.chat.ChatAppendStreamResponse;
import com.slack.api.methods.response.chat.ChatStopStreamResponse;
import com.slack.api.model.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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
        AsyncChatStreamHelper stream = slack.methodsAsync(ValidToken).asyncChatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(256));
    
        ChatAppendStreamResponse resp = stream.append("hello").get();
        assertThat(resp, is(nullValue()));
        assertThat(stream.getState(), is(AsyncChatStreamHelper.State.STARTING));
        assertThat(stream.getStreamTs(), is(nullValue()));
        assertThat(stream.getBuffer().toString(), is("hello"));
    }

    @Test
    public void append_flushes_and_starts_stream_on_first_flush() throws Exception {
        AsyncChatStreamHelper stream = slack.methodsAsync(ValidToken).asyncChatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(3));

        ChatAppendStreamResponse resp = stream.append("hey").get(); // triggers flush
        assertThat(resp.isOk(), is(true));
        assertThat(stream.getState(), is(AsyncChatStreamHelper.State.IN_PROGRESS));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
        assertThat(stream.getBuffer().toString(), is(""));
    }

    @Test
    public void stop_completes() throws Exception {
        AsyncChatStreamHelper stream = slack.methodsAsync(ValidToken).asyncChatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000));

        stream.append("hello").get(); // buffered only
        ChatStopStreamResponse stop = stream.stop().get();
        assertThat(stop.isOk(), is(true));
        assertThat(stream.getState(), is(AsyncChatStreamHelper.State.COMPLETED));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
    }

    @Test(expected = SlackChatStreamException.class)
    public void append_throws_after_completed() throws Throwable {
        AsyncChatStreamHelper stream = slack.methodsAsync(ValidToken).asyncChatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000));

        stream.stop().get();
        try {
            stream.append("nope").get();
        } catch (Exception e) {
            // unwrap ExecutionException / CompletionException
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            throw cause;
        }
    }

    @Test(expected = SlackChatStreamException.class)
    public void stop_throws_after_completed() throws Throwable {
        AsyncChatStreamHelper stream = slack.methodsAsync(ValidToken).asyncChatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000));

        stream.stop().get();
        try {
            stream.stop().get(); // second stop should throw
        } catch (ExecutionException e) {
            throw e.getCause();
        }
    }

    @Test
    public void stop_with_additional_markdown_text() throws Exception {
        AsyncChatStreamHelper stream = slack.methodsAsync(ValidToken).asyncChatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000));

        stream.append("hello ").get();
        ChatStopStreamResponse stop = stream.stop("world!").get();
        assertThat(stop.isOk(), is(true));
        assertThat(stream.getState(), is(AsyncChatStreamHelper.State.COMPLETED));
        assertThat(stream.getBuffer().toString(), is("hello world!"));
    }

    @Test
    public void stop_with_blocks_and_metadata() throws Exception {
        AsyncChatStreamHelper stream = slack.methodsAsync(ValidToken).asyncChatStreamHelper(req -> req
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
        ).get();
        assertThat(stop.isOk(), is(true));
        assertThat(stream.getState(), is(AsyncChatStreamHelper.State.COMPLETED));
    }

    @Test
    public void stop_after_stream_already_started() throws Exception {
        AsyncChatStreamHelper stream = slack.methodsAsync(ValidToken).asyncChatStreamHelper(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1)); // force immediate flush

        // Start the stream via append
        ChatAppendStreamResponse appendResp = stream.append("a").get();
        assertThat(appendResp.isOk(), is(true));
        assertThat(stream.getState(), is(AsyncChatStreamHelper.State.IN_PROGRESS));
        assertThat(stream.getStreamTs(), is(notNullValue()));

        // Now stop - should not call startStream again
        ChatStopStreamResponse stop = stream.stop().get();
        assertThat(stop.isOk(), is(true));
        assertThat(stream.getState(), is(AsyncChatStreamHelper.State.COMPLETED));
    }

    @Test
    public void default_buffer_size_is_256() {
        AsyncChatStreamHelper stream = slack.methodsAsync(ValidToken).asyncChatStreamHelper(req -> req
                .channel("C123"));

        assertThat(stream.getBufferSize(), is(256));
    }

    @Test
    public void multiple_appends_accumulate_in_buffer() throws Exception {
        AsyncChatStreamHelper stream = slack.methodsAsync(ValidToken).asyncChatStreamHelper(req -> req
                .channel("C123")
                .bufferSize(256));

        stream.append("hello").get();
        stream.append(" ").get();
        stream.append("world").get();

        assertThat(stream.getBuffer().toString(), is("hello world"));
        assertThat(stream.getState(), is(AsyncChatStreamHelper.State.STARTING));
    }
}


