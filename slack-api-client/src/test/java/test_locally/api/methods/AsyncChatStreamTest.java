package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.AsyncChatStream;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.SlackChatStreamException;
import com.slack.api.methods.request.chat.ChatAppendStreamRequest;
import com.slack.api.methods.request.chat.ChatStartStreamRequest;
import com.slack.api.methods.request.chat.ChatStopStreamRequest;
import com.slack.api.methods.response.chat.ChatAppendStreamResponse;
import com.slack.api.methods.response.chat.ChatStartStreamResponse;
import com.slack.api.methods.response.chat.ChatStopStreamResponse;
import com.slack.api.model.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static util.MockSlackApi.ValidToken;

public class AsyncChatStreamTest {

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
        AsyncChatStream stream = slack.methodsAsync(ValidToken).chatStream(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(256));

        ChatAppendStreamResponse resp = stream.append("hello").get();
        assertThat(resp, is(nullValue()));
        assertThat(stream.getStreamTs(), is(nullValue()));
    }

    @Test(expected = NullPointerException.class)
    public void append_rejects_null_markdown_text() {
        AsyncChatStream stream = slack.methodsAsync(ValidToken).chatStream(req -> req
                .channel("C123")
                .threadTs("123.123"));

        stream.append(null);
    }

    @Test
    public void append_flushes_and_starts_stream_on_first_flush() throws Exception {
        AsyncChatStream stream = slack.methodsAsync(ValidToken).chatStream(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(3));

        ChatAppendStreamResponse resp = stream.append("hey").get(); // triggers flush
        assertThat(resp.isOk(), is(true));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
    }

    @Test
    public void stop_completes() throws Exception {
        AsyncChatStream stream = slack.methodsAsync(ValidToken).chatStream(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1000));

        stream.append("hello").get(); // buffered only
        ChatStopStreamResponse stop = stream.stop().get();
        assertThat(stop.isOk(), is(true));
        assertThat(stream.getStreamTs(), is("0000000000.000000"));
    }

    @Test
    public void stop_throws_with_stop_response_when_stopStream_fails() throws Exception {
        AsyncMethodsClient client = mock(AsyncMethodsClient.class);
        ChatStartStreamResponse startResponse = new ChatStartStreamResponse();
        startResponse.setOk(true);
        startResponse.setTs("0000000000.000000");
        when(client.chatStartStream(any(ChatStartStreamRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(startResponse));

        ChatStopStreamResponse stopResponse = new ChatStopStreamResponse();
        stopResponse.setOk(false);
        stopResponse.setError("fatal_error");
        when(client.chatStopStream(any(ChatStopStreamRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(stopResponse));

        AsyncChatStream stream = AsyncChatStream.builder()
                .client(client)
                .channel("C123")
                .build();

        try {
            stream.stop("hello").get();
        } catch (ExecutionException e) {
            assertThat(e.getCause() instanceof SlackChatStreamException, is(true));
            SlackChatStreamException cause = (SlackChatStreamException) e.getCause();
            assertThat(cause.getMessage(), is("Failed to stop stream: fatal_error"));
            assertThat(cause.getStopResponse(), is(stopResponse));
            return;
        }
        org.junit.Assert.fail("Expected SlackChatStreamException");
    }

    @Test(expected = SlackChatStreamException.class)
    public void append_throws_after_completed() throws Throwable {
        AsyncChatStream stream = slack.methodsAsync(ValidToken).chatStream(req -> req
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
        AsyncChatStream stream = slack.methodsAsync(ValidToken).chatStream(req -> req
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
        AsyncMethodsClient client = mock(AsyncMethodsClient.class);
        ChatStartStreamResponse startResponse = new ChatStartStreamResponse();
        startResponse.setOk(true);
        startResponse.setTs("0000000000.000000");
        when(client.chatStartStream(any(ChatStartStreamRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(startResponse));

        ChatStopStreamResponse stopResponse = new ChatStopStreamResponse();
        stopResponse.setOk(true);
        when(client.chatStopStream(any(ChatStopStreamRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(stopResponse));

        AsyncChatStream stream = AsyncChatStream.builder()
                .client(client)
                .channel("C123")
                .build();

        stream.append("hello ").get();
        ChatStopStreamResponse stop = stream.stop("world!").get();
        assertThat(stop.isOk(), is(true));
        verify(client).chatStopStream(argThat((ChatStopStreamRequest req) -> "hello world!".equals(req.getMarkdownText())));
    }

    @Test
    public void stop_with_blocks_and_metadata() throws Exception {
        AsyncChatStream stream = slack.methodsAsync(ValidToken).chatStream(req -> req
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
    }

    @Test
    public void stop_after_stream_already_started() throws Exception {
        AsyncChatStream stream = slack.methodsAsync(ValidToken).chatStream(req -> req
                .channel("C123")
                .threadTs("123.123")
                .bufferSize(1)); // force immediate flush

        // Start the stream via append
        ChatAppendStreamResponse appendResp = stream.append("a").get();
        assertThat(appendResp.isOk(), is(true));
        assertThat(stream.getStreamTs(), is(notNullValue()));

        // Now stop - should not call startStream again
        ChatStopStreamResponse stop = stream.stop().get();
        assertThat(stop.isOk(), is(true));
    }

    @Test
    public void default_buffer_size_is_256() {
        AsyncChatStream stream = slack.methodsAsync(ValidToken).chatStream(req -> req
                .channel("C123"));

        assertThat(stream.getBufferSize(), is(256));
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejects_zero_buffer_size() {
        slack.methodsAsync(ValidToken).chatStream(req -> req
                .channel("C123")
                .bufferSize(0));
    }

    @Test
    public void multiple_appends_accumulate_in_buffer() throws Exception {
        AsyncMethodsClient client = mock(AsyncMethodsClient.class);
        ChatStartStreamResponse startResponse = new ChatStartStreamResponse();
        startResponse.setOk(true);
        startResponse.setTs("0000000000.000000");
        when(client.chatStartStream(any(ChatStartStreamRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(startResponse));

        ChatStopStreamResponse stopResponse = new ChatStopStreamResponse();
        stopResponse.setOk(true);
        when(client.chatStopStream(any(ChatStopStreamRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(stopResponse));

        AsyncChatStream stream = AsyncChatStream.builder()
                .client(client)
                .channel("C123")
                .bufferSize(256)
                .build();

        stream.append("hello").get();
        stream.append(" ").get();
        stream.append("world").get();

        stream.stop().get();
        verify(client).chatStopStream(argThat((ChatStopStreamRequest req) -> "hello world".equals(req.getMarkdownText())));
    }

    @Test
    public void append_api_calls_are_serialized() throws Exception {
        AsyncMethodsClient client = mock(AsyncMethodsClient.class);
        CompletableFuture<ChatStartStreamResponse> startFuture = new CompletableFuture<>();
        when(client.chatStartStream(any(ChatStartStreamRequest.class))).thenReturn(startFuture);

        ChatAppendStreamResponse appendResponse = new ChatAppendStreamResponse();
        appendResponse.setOk(true);
        when(client.chatAppendStream(any(ChatAppendStreamRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(appendResponse));

        AsyncChatStream stream = AsyncChatStream.builder()
                .client(client)
                .channel("C123")
                .bufferSize(1)
                .build();

        CompletableFuture<ChatAppendStreamResponse> first = stream.append("a");
        CompletableFuture<ChatAppendStreamResponse> second = stream.append("b");

        verify(client).chatStartStream(argThat((ChatStartStreamRequest req) -> "a".equals(req.getMarkdownText())));
        verify(client, never()).chatAppendStream(any(ChatAppendStreamRequest.class));

        ChatStartStreamResponse startResponse = new ChatStartStreamResponse();
        startResponse.setOk(true);
        startResponse.setTs("0000000000.000000");
        startFuture.complete(startResponse);

        assertThat(first.get().isOk(), is(true));
        assertThat(second.get().isOk(), is(true));
        verify(client).chatAppendStream(argThat((ChatAppendStreamRequest req) -> "b".equals(req.getMarkdownText())));
    }

    @Test
    public void queued_stop_uses_restored_buffer_after_failed_append() throws Exception {
        AsyncMethodsClient client = mock(AsyncMethodsClient.class);
        ChatStartStreamResponse startResponse = new ChatStartStreamResponse();
        startResponse.setOk(true);
        startResponse.setTs("0000000000.000000");
        when(client.chatStartStream(any(ChatStartStreamRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(startResponse));

        CompletableFuture<ChatAppendStreamResponse> appendFuture = new CompletableFuture<>();
        when(client.chatAppendStream(any(ChatAppendStreamRequest.class))).thenReturn(appendFuture);

        ChatStopStreamResponse stopResponse = new ChatStopStreamResponse();
        stopResponse.setOk(true);
        when(client.chatStopStream(any(ChatStopStreamRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(stopResponse));

        AsyncChatStream stream = AsyncChatStream.builder()
                .client(client)
                .channel("C123")
                .bufferSize(1)
                .build();

        assertThat(stream.append("a").get().isOk(), is(true));
        CompletableFuture<ChatAppendStreamResponse> failedAppend = stream.append("b");
        CompletableFuture<ChatStopStreamResponse> stop = stream.stop("c");

        ChatAppendStreamResponse appendResponse = new ChatAppendStreamResponse();
        appendResponse.setOk(false);
        appendResponse.setError("fatal_error");
        appendFuture.complete(appendResponse);

        try {
            failedAppend.get();
            org.junit.Assert.fail("Expected SlackChatStreamException");
        } catch (ExecutionException e) {
            assertThat(e.getCause() instanceof SlackChatStreamException, is(true));
        }

        assertThat(stop.get().isOk(), is(true));
        verify(client).chatStopStream(argThat((ChatStopStreamRequest req) -> "bc".equals(req.getMarkdownText())));
    }
}
