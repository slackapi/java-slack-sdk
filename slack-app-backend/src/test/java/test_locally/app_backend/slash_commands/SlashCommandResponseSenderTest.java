package test_locally.app_backend.slash_commands;

import com.slack.api.Slack;
import com.slack.api.app_backend.slash_commands.SlashCommandResponseSender;
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.webhook.WebhookResponse;
import config.SlackTestConfig;
import org.junit.Test;
import util.MockWebhookServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SlashCommandResponseSenderTest {

    MockWebhookServer slackApiServer = new MockWebhookServer();

    @Test
    public void test() throws Exception {
        slackApiServer.start();
        try {
            Slack slack = Slack.getInstance(SlackTestConfig.get());
            SlashCommandResponseSender sender = new SlashCommandResponseSender(slack);
            SlashCommandResponse data = SlashCommandResponse.builder()
                    .text("Hi there!")
                    .responseType("in_channel")
                    .threadTs("111.222")
                    .build();
            WebhookResponse response = sender.send(slackApiServer.getWebhookUrl(), data);
            assertNotNull(response);
            assertEquals(200L, response.getCode().longValue());
            assertEquals("OK", response.getBody());
        } finally {
            slackApiServer.stop();
        }

    }
}
