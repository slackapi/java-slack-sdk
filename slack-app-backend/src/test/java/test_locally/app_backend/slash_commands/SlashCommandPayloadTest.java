package test_locally.app_backend.slash_commands;

import com.slack.api.app_backend.slash_commands.SlashCommandPayloadDetector;
import com.slack.api.app_backend.slash_commands.SlashCommandPayloadParser;
import com.slack.api.app_backend.slash_commands.payload.SlashCommandPayload;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class SlashCommandPayloadTest {

    SlashCommandPayloadParser parser = new SlashCommandPayloadParser();

    // https://docs.slack.dev/interactivity/implementing-slash-commands
    String body1 = "token=gIkuvaNzQIHg97ATvDxqgjtO" +
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
            "&trigger_id=13345224609.738474920.8088930838d88f008e0" +
            "&api_app_id=A123456";

    // (modified) real payload
    String body2 = "token=xxx&" +
            "team_id=T111&" +
            "team_domain=test-workspace&" +
            "channel_id=C111&" +
            "channel_name=general&" +
            "user_id=W111&" +
            "user_name=primary-owner&" +
            "command=%2Fhello&" +
            "text=&" +
            "api_app_id=A111&" +
            "enterprise_id=E111&" +
            "enterprise_name=Sandbox+Org&" +
            "response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT111%2F111%2Fxxx&" +
            "trigger_id=111.222.xxx";

    String orgLevelApp = "token=xxx&" +
            "team_id=T111&" +
            "team_domain=test-workspace&" +
            "channel_id=C111&" +
            "channel_name=dev&" +
            "user_id=W111&" +
            "user_name=primary-owner&" +
            "command=%2Forg-level-command&" +
            "text=test&" +
            "api_app_id=A111&" +
            "is_enterprise_install=true&" +
            "enterprise_id=E111&" +
            "enterprise_name=Sandbox+Org&" +
            "response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT111%2F111%2Fxxx&" +
            "trigger_id=111.222.xxx";

    @Test
    public void parse_1() {
        SlashCommandPayload payload = parser.parse(body1);

        assertThat(payload.getToken(), is("gIkuvaNzQIHg97ATvDxqgjtO"));
        assertThat(payload.getTeamId(), is("T0001"));
        assertThat(payload.getTeamDomain(), is("example"));
        assertThat(payload.getEnterpriseId(), is("E0001"));
        assertThat(payload.getEnterpriseName(), is("Globular Construct Inc"));
        assertThat(payload.getApiAppId(), is("A123456"));
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
    public void parse_2() {
        SlashCommandPayload payload = parser.parse(body2);

        assertThat(payload.getToken(), is("xxx"));
        assertThat(payload.getTeamId(), is("T111"));
        assertThat(payload.getTeamDomain(), is("test-workspace"));
        assertThat(payload.getEnterpriseId(), is("E111"));
        assertThat(payload.getEnterpriseName(), is("Sandbox Org"));
        assertThat(payload.getApiAppId(), is("A111"));
        assertThat(payload.getChannelId(), is("C111"));
        assertThat(payload.getChannelName(), is("general"));
        assertThat(payload.getUserId(), is("W111"));
        assertThat(payload.getUserName(), is("primary-owner"));
        assertThat(payload.getCommand(), is("/hello"));
        assertThat(payload.getText(), is(nullValue()));
        assertThat(payload.getResponseUrl(), is("https://hooks.slack.com/commands/T111/111/xxx"));
        assertThat(payload.getTriggerId(), is("111.222.xxx"));
        assertThat(payload.isEnterpriseInstall(), is(false));
    }

    @Test
    public void detect() {
        SlashCommandPayloadDetector detector = new SlashCommandPayloadDetector();
        assertTrue(detector.isCommand(body1));
    }

    @Test
    public void parse_org_level_app() {
        SlashCommandPayload payload = parser.parse(orgLevelApp);

        assertThat(payload.getToken(), is("xxx"));
        assertThat(payload.getTeamId(), is("T111"));
        assertThat(payload.getTeamDomain(), is("test-workspace"));
        assertThat(payload.getEnterpriseId(), is("E111"));
        assertThat(payload.getEnterpriseName(), is("Sandbox Org"));
        assertThat(payload.getApiAppId(), is("A111"));
        assertThat(payload.getChannelId(), is("C111"));
        assertThat(payload.getChannelName(), is("dev"));
        assertThat(payload.getUserId(), is("W111"));
        assertThat(payload.getUserName(), is("primary-owner"));
        assertThat(payload.getCommand(), is("/org-level-command"));
        assertThat(payload.getText(), is("test"));
        assertThat(payload.getResponseUrl(), is("https://hooks.slack.com/commands/T111/111/xxx"));
        assertThat(payload.getTriggerId(), is("111.222.xxx"));
        assertThat(payload.isEnterpriseInstall(), is(true));
    }
}
