package test_locally.app_backend.outgoing_webhooks.payload;

import com.slack.api.app_backend.outgoing_webhooks.payload.WebhookPayload;
import com.slack.api.app_backend.outgoing_webhooks.WebhookPayloadParser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class WebhookPayloadParserTest {

    WebhookPayloadParser parser = new WebhookPayloadParser();

    @Test
    public void parse_empty() {
        String body = "";
        WebhookPayload payload = parser.parse(body);
        assertThat(payload, is(notNullValue()));
    }

    @Test
    public void parse_normal() {
        String body = "token=token-value&" +
                "team_id=T12345678&" +
                "team_domain=team-domain&" +
                "service_id=123456789&" +
                "channel_id=C12345678&" +
                "channel_name=channel-name&" +
                "timestamp=1560563534.001700&" +
                "user_id=U12345678&" +
                "user_name=user-name&" +
                "text=this+is+an+outgoing+test&" +
                "trigger_word=outgoing";
        WebhookPayload payload = parser.parse(body);
        assertThat(payload, is(notNullValue()));
        assertThat(payload.getToken(), is("token-value"));
        assertThat(payload.getTeamId(), is("T12345678"));
        assertThat(payload.getTeamDomain(), is("team-domain"));
        assertThat(payload.getServiceId(), is("123456789"));
        assertThat(payload.getChannelId(), is("C12345678"));
        assertThat(payload.getChannelName(), is("channel-name"));
        assertThat(payload.getTimestamp(), is("1560563534.001700"));
        assertThat(payload.getUserId(), is("U12345678"));
        assertThat(payload.getUserName(), is("user-name"));
        assertThat(payload.getText(), is("this is an outgoing test"));
        assertThat(payload.getTriggerWord(), is("outgoing"));
    }

}
