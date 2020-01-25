package test_locally.api.rtm.message;

import com.slack.api.rtm.message.Message;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageTest {

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"message\",\n" +
                "    \"channel\": \"c\",\n" +
                "    \"text\": \"foo\",\n" +
                "    \"id\": \"123\"\n" +
                "}";
        Message msg = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertThat(msg.getType(), is("message"));
        assertThat(msg.getChannel(), is("c"));
        assertThat(msg.getText(), is("foo"));
        assertThat(msg.getId(), is(123L));
    }

    @Test
    public void serialize() {
        String generatedJson = Message.builder().build().toJSONString();
        String expectedJson = "{\"type\":\"message\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
