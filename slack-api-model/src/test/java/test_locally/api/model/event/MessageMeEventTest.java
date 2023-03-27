package test_locally.api.model.event;

import com.slack.api.model.event.MessageMeEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageMeEventTest {

    @Test
    public void typeName() {
        assertThat(MessageMeEvent.TYPE_NAME, is("message"));
        assertThat(MessageMeEvent.SUBTYPE_NAME, is("me_message"));
    }

    @Test
    public void test() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"me_message\",\n" +
                "  \"text\": \"hey\",\n" +
                "  \"user\": \"U03E94MK0\",\n" +
                "  \"ts\": \"1679895454.657169\",\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"rich_text\",\n" +
                "      \"block_id\": \"YUITq\",\n" +
                "      \"elements\": [\n" +
                "        {\n" +
                "          \"type\": \"rich_text_section\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"hey\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"channel\": \"C03E94MKS\",\n" +
                "  \"event_ts\": \"1679895454.657169\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}";
        MessageMeEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageMeEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("me_message"));
        assertThat(event.getUser(), is("U03E94MK0"));
        assertThat(event.getText(), is("hey"));
        assertThat(event.getBlocks().size(), is(1));
    }
}
