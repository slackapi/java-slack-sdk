package test_locally.api.model.event;

import com.slack.api.model.event.MessageChannelPostingPermissionsEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageChannelPostingPermissionsEventTest {

    @Test
    public void typeName() {
        assertThat(MessageChannelPostingPermissionsEvent.TYPE_NAME, is("message"));
        assertThat(MessageChannelPostingPermissionsEvent.SUBTYPE_NAME, is("channel_posting_permissions"));
    }

    @Test
    public void deserialize_simple() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"channel_posting_permissions\",\n" +
                "  \"ts\": \"1614215651.001300\",\n" +
                "  \"user\": \"W111\",\n" +
                "  \"text\": \"changed channel posting permissions.\",\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"event_ts\": \"1614215651.001300\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}";
        MessageChannelPostingPermissionsEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageChannelPostingPermissionsEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("channel_posting_permissions"));
    }

}
