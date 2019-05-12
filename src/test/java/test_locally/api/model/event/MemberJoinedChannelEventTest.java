package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.MemberJoinedChannelEvent;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MemberJoinedChannelEventTest {

    @Test
    public void typeName() {
        assertThat(MemberJoinedChannelEvent.TYPE_NAME, is("member_joined_channel"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"member_joined_channel\",\n" +
                "    \"user\": \"W06GH7XHN\",\n" +
                "    \"channel\": \"C0698JE0H\",\n" +
                "    \"channel_type\": \"C\",\n" +
                "    \"team\": \"T024BE7LD\",\n" +
                "    \"inviter\": \"U123456789\"\n" +
                "}";
        MemberJoinedChannelEvent event = GsonFactory.createSnakeCase().fromJson(json, MemberJoinedChannelEvent.class);
        assertThat(event.getType(), is("member_joined_channel"));
        assertThat(event.getUser(), is("W06GH7XHN"));
        assertThat(event.getChannel(), is("C0698JE0H"));
        assertThat(event.getChannelType(), is("C"));
        assertThat(event.getTeam(), is("T024BE7LD"));
        assertThat(event.getInviter(), is("U123456789"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        MemberJoinedChannelEvent event = new MemberJoinedChannelEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"member_joined_channel\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}