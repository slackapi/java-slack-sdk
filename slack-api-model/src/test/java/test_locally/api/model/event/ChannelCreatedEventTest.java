package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.ChannelCreatedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChannelCreatedEventTest {

    @Test
    public void typeName() {
        assertThat(ChannelCreatedEvent.TYPE_NAME, is("channel_created"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"channel_created\",\n" +
                "    \"channel\": {\n" +
                "        \"id\": \"C024BE91L\",\n" +
                "        \"is_channel\": true,\n" +
                "        \"is_org_shared\": false,\n" +
                "        \"is_shared\": false,\n" +
                "        \"name\": \"fun\",\n" +
                "        \"name\": \"fun\",\n" +
                "        \"name_normalized\": \"fun\",\n" +
                "        \"created\": 1360782804,\n" +
                "        \"creator\": \"U024BE7LH\"\n" +
                "    }\n" +
                "}";
        ChannelCreatedEvent event = GsonFactory.createSnakeCase().fromJson(json, ChannelCreatedEvent.class);
        assertThat(event.getType(), is("channel_created"));
        assertThat(event.getChannel(), is(notNullValue()));
        assertThat(event.getChannel().getId(), is("C024BE91L"));
        assertThat(event.getChannel().getName(), is("fun"));
        assertThat(event.getChannel().getCreated(), is(1360782804));
        assertThat(event.getChannel().getCreator(), is("U024BE7LH"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ChannelCreatedEvent event = new ChannelCreatedEvent();
        event.setChannel(new ChannelCreatedEvent.Channel());
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"channel_created\",\"channel\":{\"is_channel\":false,\"is_shared\":false,\"is_org_shared\":false,\"is_read_only\":false,\"is_thread_only\":false,\"is_archived\":false,\"is_member\":false,\"is_general\":false,\"is_group\":false,\"is_im\":false,\"is_private\":false,\"is_mpim\":false,\"is_file\":false,\"is_global_shared\":false,\"is_org_default\":false,\"is_org_mandatory\":false,\"is_ext_shared\":false,\"is_pending_ext_shared\":false}}";
        assertThat(generatedJson, is(expectedJson));
    }

}
