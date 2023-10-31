package test_locally.api.rtm;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.User;
import com.slack.api.rtm.RTMClient;
import org.junit.Test;

import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RTMClientTest {


    @Test
    public void instantiation() throws URISyntaxException {
        RTMClient client = new RTMClient(Slack.getInstance(), "xoxb-123", "wss://xxxx", new User());
        assertNotNull(client);
    }

    @Test(expected = IllegalArgumentException.class)
    public void instantiation_wssUrl_is_null() throws URISyntaxException {
        RTMClient client = new RTMClient(Slack.getInstance(), "xoxb-123", null, new User());
        assertNotNull(client);
    }

    @Test(expected = IllegalArgumentException.class)
    public void instantiation_connected_user_is_null() throws URISyntaxException {
        RTMClient client = new RTMClient(Slack.getInstance(), "xoxb-123", "wss://xxxx", null);
        assertNotNull(client);
    }

    @Test(expected = DeploymentException.class)
    public void connect() throws IOException, DeploymentException, URISyntaxException {
        RTMClient client = new RTMClient(Slack.getInstance(), "xoxb-123", "wss://xxxx", new User());
        client.connect();
    }

    @Test(expected = IllegalStateException.class)
    public void reconnect() throws IOException, DeploymentException, URISyntaxException, SlackApiException {
        RTMClient client = new RTMClient(Slack.getInstance(), "xoxb-123", "wss://xxxx", new User());
        client.reconnect();
    }

    @Test
    public void disconnect() throws IOException, URISyntaxException {
        RTMClient client = new RTMClient(Slack.getInstance(), "xoxb-123", "wss://xxxx", new User());
        client.disconnect();
    }

    @Test
    public void close() throws IOException, URISyntaxException {
        RTMClient client = new RTMClient(Slack.getInstance(), "xoxb-123", "wss://xxxx", new User());
        client.close();
    }

    @Test
    public void onOpen() throws URISyntaxException {
        RTMClient client = new RTMClient(Slack.getInstance(), "xoxb-123", "wss://xxxx", new User());
        Session session = mock(Session.class);
        client.onOpen(session);
    }

    @Test
    public void onClose() throws URISyntaxException {
        RTMClient client = new RTMClient(Slack.getInstance(), "xoxb-123", "wss://xxxx", new User());
        Session session = mock(Session.class);
        client.onClose(session, new CloseReason(CloseReason.CloseCodes.NO_STATUS_CODE, "msg"));
    }

    @Test
    public void onError() throws URISyntaxException {
        RTMClient client = new RTMClient(Slack.getInstance(), "xoxb-123", "wss://xxxx", new User());
        Session session = mock(Session.class);
        client.onError(session, new Exception());
    }

    @Test
    public void onMessage() throws URISyntaxException {
        RTMClient client = new RTMClient(Slack.getInstance(), "xoxb-123", "wss://xxxx", new User());
        client.onMessage("msg");
    }

    @Test
    public void testUpdateSession() throws URISyntaxException, IOException {
        RTMClient client = new RTMClient(Slack.getInstance(), "rac-123", "wss://rachit", new User());
        Session currentSession = mock(Session.class);
        Session newSession = mock(Session.class);
        client.updateSession(currentSession);
        client.updateSession(newSession);
        verify(currentSession).close(any(CloseReason.class));
        assertNotNull(currentSession);
    }
}
