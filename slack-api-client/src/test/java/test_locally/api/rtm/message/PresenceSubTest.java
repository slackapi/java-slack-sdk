package test_locally.api.rtm.message;

import com.slack.api.rtm.message.PresenceSub;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PresenceSubTest {

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"presence_sub\",\n" +
                "    \"ids\": [\n" +
                "        \"U061F7AUR\",\n" +
                "        \"W123456\"\n" +
                "    ]\n" +
                "}";
        PresenceSub q = GsonFactory.createSnakeCase().fromJson(json, PresenceSub.class);
        assertThat(q.getType(), is("presence_sub"));
        assertThat(q.getIds(), is(Arrays.asList("U061F7AUR", "W123456")));
    }

    @Test
    public void serialize() {
        String generatedJson = PresenceSub.builder().ids(Arrays.asList("1", "2")).build().toJSONString();
        String expectedJson = "{\"type\":\"presence_sub\",\"ids\":[\"1\",\"2\"]}";
        assertThat(generatedJson, is(expectedJson));
    }

}
