package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.ChatStream;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackChatStreamException;
import com.slack.api.methods.request.chat.ChatStartStreamRequest;
import com.slack.api.methods.request.chat.ChatStopStreamRequest;
import com.slack.api.methods.response.chat.ChatAppendStreamResponse;
import com.slack.api.methods.response.chat.ChatStartStreamResponse;
import com.slack.api.methods.response.chat.ChatStopStreamResponse;
import com.slack.api.model.Message;
import org.junit.After;
import org.junit.Assert;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static util.MockSlackApi.ValidToken;

public class ChatStreamTest {

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
        ChatStream stream = slack.methods(ValidToken).chatStream(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(256));

        ChatAppendStreamResponse resp = stream.append("hello");
        assertThat(resp, is(nullValue()));
        assertThat(stream.getStreamTs(), is(nullValue()));
    }

    @Test(expected = NullPointerException.class)
    public void append_rejects_null_markdown_text() throws Exception {
        ChatStream stream = slack.methods(ValidToken).chatStream(req -> req
                .channel("C123")
                .threadTs("123.123"));

        stream.append(null);
    }

    @Test
    public void append_flushes_and_starts_stream_on_first_flush() throws Exception {
        ChatStream stream = slack.methods(ValidToken).chatStream(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(3));

        ChatAppendStreamResponse resp = stream.append("hey"); // triggers flush
        assertThat(resp.isOk(), is(true));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
    }

    @Test
    public void append_flushes_with_appendStream_after_started() throws Exception {
        ChatStream stream = slack.methods(ValidToken).chatStream(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1));

        // first flush uses chat.startStream
        ChatAppendStreamResponse first = stream.append("a");
        assertThat(first.isOk(), is(true));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));

        // second flush uses chat.appendStream
        ChatAppendStreamResponse second = stream.append("b");
        assertThat(second.isOk(), is(true));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
    }

    @Test
    public void stop_starts_stream_if_needed_and_completes() throws Exception {
        ChatStream stream = slack.methods(ValidToken).chatStream(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000));

        stream.append("hello"); // buffered only
        ChatStopStreamResponse stop = stream.stop();
        assertThat(stop.isOk(), is(true));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
    }

    @Test
    public void stop_throws_with_stop_response_when_stopStream_fails() throws Exception {
        MethodsClient client = mock(MethodsClient.class);
        ChatStartStreamResponse startResponse = new ChatStartStreamResponse();
        startResponse.setOk(true);
        startResponse.setTs("0000000000.000000");
        when(client.chatStartStream(any(ChatStartStreamRequest.class))).thenReturn(startResponse);

        ChatStopStreamResponse stopResponse = new ChatStopStreamResponse();
        stopResponse.setOk(false);
        stopResponse.setError("fatal_error");
        when(client.chatStopStream(any(ChatStopStreamRequest.class))).thenReturn(stopResponse);

        ChatStream stream = ChatStream.builder()
                .client(client)
                .channel("C123")
                .build();

        try {
            stream.stop("hello");
            Assert.fail("Expected SlackChatStreamException");
        } catch (SlackChatStreamException e) {
            assertThat(e.getMessage(), is("Failed to stop stream: fatal_error"));
            assertThat(e.getStopResponse(), is(stopResponse));
        }
    }

    @Test(expected = SlackChatStreamException.class)
    public void append_throws_after_completed() throws Exception {
        ChatStream stream = slack.methods(ValidToken).chatStream(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000));

        stream.stop();
        stream.append("nope");
    }

    @Test(expected = SlackChatStreamException.class)
    public void stop_throws_after_completed() throws Exception {
        ChatStream stream = slack.methods(ValidToken).chatStream(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000));

        stream.stop();
        stream.stop(); // second stop should throw
    }

    @Test
    public void stop_with_additional_markdown_text() throws Exception {
        MethodsClient client = mock(MethodsClient.class);
        ChatStartStreamResponse startResponse = new ChatStartStreamResponse();
        startResponse.setOk(true);
        startResponse.setTs("0000000000.000000");
        when(client.chatStartStream(any(ChatStartStreamRequest.class))).thenReturn(startResponse);

        ChatStopStreamResponse stopResponse = new ChatStopStreamResponse();
        stopResponse.setOk(true);
        when(client.chatStopStream(any(ChatStopStreamRequest.class))).thenReturn(stopResponse);

        ChatStream stream = ChatStream.builder()
                .client(client)
                .channel("C123")
                .build();

        stream.append("hello ");
        ChatStopStreamResponse stop = stream.stop("world!");
        assertThat(stop.isOk(), is(true));
        verify(client).chatStopStream(argThat((ChatStopStreamRequest req) -> "hello world!".equals(req.getMarkdownText())));
    }

    @Test
    public void stop_with_blocks_and_metadata() throws Exception {
        ChatStream stream = slack.methods(ValidToken).chatStream(req -> req
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
    }

    @Test
    public void stop_after_stream_already_started() throws Exception {
        ChatStream stream = slack.methods(ValidToken).chatStream(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1)); // force immediate flush

        // Start the stream via append
        ChatAppendStreamResponse appendResp = stream.append("a");
        assertThat(appendResp.isOk(), is(true));
        assertThat(stream.getStreamTs(), is(notNullValue()));

        // Now stop - should not call startStream again
        ChatStopStreamResponse stop = stream.stop();
        assertThat(stop.isOk(), is(true));
    }

    @Test
    public void default_buffer_size_is_256() {
        ChatStream stream = slack.methods(ValidToken).chatStream(req -> req
                .channel("C123"));

        assertThat(stream.getBufferSize(), is(256));
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejects_zero_buffer_size() {
        slack.methods(ValidToken).chatStream(req -> req
                .channel("C123")
                .bufferSize(0));
    }

    @Test
    public void multiple_appends_accumulate_in_buffer() throws Exception {
        MethodsClient client = mock(MethodsClient.class);
        ChatStartStreamResponse startResponse = new ChatStartStreamResponse();
        startResponse.setOk(true);
        startResponse.setTs("0000000000.000000");
        when(client.chatStartStream(any(ChatStartStreamRequest.class))).thenReturn(startResponse);

        ChatStopStreamResponse stopResponse = new ChatStopStreamResponse();
        stopResponse.setOk(true);
        when(client.chatStopStream(any(ChatStopStreamRequest.class))).thenReturn(stopResponse);

        ChatStream stream = ChatStream.builder()
                .client(client)
                .channel("C123")
                .bufferSize(256)
                .build();

        stream.append("hello");
        stream.append(" ");
        stream.append("world");

        stream.stop();
        verify(client).chatStopStream(argThat((ChatStopStreamRequest req) -> "hello world".equals(req.getMarkdownText())));
    }
}
