package test_locally.app;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.OAuthStartRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.builtin.ClientOnlyOAuthStateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@Slf4j
public class OAuthStartTest {

    @Test
    public void start() throws Exception {
        App app = new App(AppConfig.builder()
                .signingSecret("secret")
                .clientId("111.222")
                .clientSecret("secret")
                .scope("commands,chat:write")
                .build());
        app.service(new ClientOnlyOAuthStateService() {
            @Override
            public String issueNewState(Request request, Response response) {
                return "generated-state-value";
            }
        });

        OAuthStartRequest req = new OAuthStartRequest(null, new RequestHeaders(Collections.emptyMap()));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
        assertEquals("text/html; charset=utf-8", response.getContentType());
        assertEquals("<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "body {\n" +
                "  padding: 10px 15px;\n" +
                "  font-family: verdana;\n" +
                "  text-align: center;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h2>Slack App Installation</h2>\n" +
                "<p><a href=\"https://slack.com/oauth/v2/authorize?client_id=111.222&scope=commands,chat:write&user_scope=&state=generated-state-value\"><img alt=\"\"Add to Slack\"\" height=\"40\" width=\"139\" src=\"https://platform.slack-edge.com/img/add_to_slack.png\" srcset=\"https://platform.slack-edge.com/img/add_to_slack.png 1x, https://platform.slack-edge.com/img/add_to_slack@2x.png 2x\" /></a></p>\n" +
                "</body>\n" +
                "</html>", response.getBody());
    }
}
