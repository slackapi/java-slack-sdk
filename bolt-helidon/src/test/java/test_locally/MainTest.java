package test_locally;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.helidon.SlackAppServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static test_locally.Main.apiApp;
import static test_locally.Main.oauthApp;

public class MainTest {

    private static SlackAppServer server;

    AppConfig appConfig = AppConfig.builder()
            .signingSecret("secret")
            .clientId("123.123")
            .clientSecret("secret")
            .scope("app_mention:read,chat:write,commands")
            .oauthStartPath("/slack/oauth/start")
            .oauthCallbackPath("/slack/oauth/callback")
            .redirectUri("https://www.example.com/slack/outh/callback")
            .oauthCompletionUrl("https://wwww.example.com/thank-you")
            .oauthCancellationUrl("https://www.example.com/something-wrong")
            .oAuthInstallPageRenderingEnabled(false)
            .build();

    @Before
    public void startTheServer() throws InterruptedException {
        server = new SlackAppServer(apiApp(appConfig));
        server.start();
        Thread.sleep(100L);
    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }

    private HttpURLConnection connect(String method, String path) throws Exception {
        URL url = new URL("http://localhost:" + server.getPort() + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        return conn;
    }

    @Test
    public void api() throws Exception {
        HttpURLConnection conn = connect("POST", "/slack/events");
        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        os.write("token=xxx&ssl_check=1".getBytes());
        os.close();
        assertEquals(200, conn.getResponseCode());
    }

    @Test
    public void oauthStart() throws Exception {
        HttpURLConnection conn = connect("GET", "/slack/oauth/start");
        assertEquals(404, conn.getResponseCode());

        server.stop();
        server = new SlackAppServer(apiApp(appConfig), oauthApp(appConfig));
        server.start();
        Thread.sleep(200L);

        conn = connect("GET", "/slack/oauth/start");
        assertEquals(302, conn.getResponseCode());
        String authorizationUrl = conn.getHeaderField("Location");
        assertTrue(authorizationUrl, authorizationUrl.startsWith(
                "https://slack.com/oauth/v2/authorize?client_id=123.123&scope=app_mention:read,chat:write,commands&user_scope=&state="));
    }

}
