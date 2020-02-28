package test_locally.app_backend.slash_commands;

import com.slack.api.app_backend.slash_commands.SlashCommandPayloadDetector;
import com.slack.api.app_backend.slash_commands.SlashCommandPayloadParser;
import com.slack.api.app_backend.slash_commands.payload.SlashCommandPayload;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class SlashCommandPayloadTest {

    SlashCommandPayloadParser parser = new SlashCommandPayloadParser();

    String body = "token=gIkuvaNzQIHg97ATvDxqgjtO" +
            "&team_id=T0001" +
            "&team_domain=example" +
            "&enterprise_id=E0001" +
            "&enterprise_name=Globular%20Construct%20Inc" +
            "&channel_id=C2147483705" +
            "&channel_name=test" +
            "&user_id=U2147483697" +
            "&user_name=Steve" +
            "&command=/weather" +
            "&text=94070" +
            "&response_url=https://hooks.slack.com/commands/1234/5678" +
            "&trigger_id=13345224609.738474920.8088930838d88f008e0";

    @Test
    public void parse() {
        SlashCommandPayload payload = parser.parse(body);

        assertThat(payload.getToken(), is("gIkuvaNzQIHg97ATvDxqgjtO"));
        assertThat(payload.getTeamId(), is("T0001"));
        assertThat(payload.getTeamDomain(), is("example"));
        assertThat(payload.getEnterpriseId(), is("E0001"));
        assertThat(payload.getEnterpriseName(), is("Globular Construct Inc"));
        assertThat(payload.getChannelId(), is("C2147483705"));
        assertThat(payload.getChannelName(), is("test"));
        assertThat(payload.getUserId(), is("U2147483697"));
        assertThat(payload.getUserName(), is("Steve"));
        assertThat(payload.getCommand(), is("/weather"));
        assertThat(payload.getText(), is("94070"));
        assertThat(payload.getResponseUrl(), is("https://hooks.slack.com/commands/1234/5678"));
        assertThat(payload.getTriggerId(), is("13345224609.738474920.8088930838d88f008e0"));
    }

    @Test
    public void detect() {
        SlashCommandPayloadDetector detector = new SlashCommandPayloadDetector();
        assertTrue(detector.isCommand(body));
    }
}
