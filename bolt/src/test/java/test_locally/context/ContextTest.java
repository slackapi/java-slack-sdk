package test_locally.context;

import com.slack.api.Slack;
import com.slack.api.bolt.context.Context;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContextTest {

    @Test
    public void say() throws IOException, SlackApiException {
        MethodsClient client = mock(MethodsClient.class);
        ChatPostMessageResponse response = new ChatPostMessageResponse();
        response.setOk(true);
        response.setTs("123.123");
        when(client.chatPostMessage(any(ChatPostMessageRequest.class))).thenReturn(response);
        Context context = new Context() {
            @Override
            public MethodsClient client() {
                return client;
            }
        };
        ChatPostMessageResponse result = context.say(r -> r.text("hi"));
        assertTrue(result.isOk());
        assertEquals("123.123", result.getTs());
    }

    @Test
    public void asyncClient() {
        Context context = new Context() {
            @Override
            public Slack getSlack() {
                return Slack.getInstance();
            }
        };
        AsyncMethodsClient client = context.asyncClient();
        assertNotNull(client);
    }
}
