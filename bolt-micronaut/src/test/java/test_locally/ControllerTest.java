package test_locally;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.micronaut.SlackAppController;
import com.slack.api.bolt.micronaut.SlackAppMicronautAdapter;
import io.micronaut.core.convert.DefaultConversionService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.simple.SimpleHttpHeaders;
import io.micronaut.http.simple.SimpleHttpParameters;
import org.junit.Test;
import util.AuthTestMockServer;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllerTest {

    @Test
    public void test() throws Exception {
        AuthTestMockServer slackApiServer = new AuthTestMockServer();
        slackApiServer.start();
        try {
            SlackConfig slackConfig = new SlackConfig();
            slackConfig.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
            Slack slack = Slack.getInstance(slackConfig);
            AppConfig config = AppConfig.builder().slack(slack)
                    .singleTeamBotToken(AuthTestMockServer.ValidToken)
                    .signingSecret("secret")
                    .build();
            SlackAppController controller = new SlackAppController(new App(config), new SlackAppMicronautAdapter(config));
            assertNotNull(controller);

            HttpRequest<String> req = mock(HttpRequest.class);
            SimpleHttpHeaders headers = new SimpleHttpHeaders(new HashMap<>(), new DefaultConversionService());
            when(req.getHeaders()).thenReturn(headers);
            SimpleHttpParameters parameters = new SimpleHttpParameters(new HashMap<>(), new DefaultConversionService());
            when(req.getParameters()).thenReturn(parameters);

            HttpResponse<String> response = controller.dispatch(req, "token=random&ssl_check=1");
            assertEquals(200, response.getStatus().getCode());

        } finally {
            slackApiServer.stop();
        }
    }

}
