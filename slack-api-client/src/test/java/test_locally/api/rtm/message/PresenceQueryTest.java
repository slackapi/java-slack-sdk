package test_locally.api.rtm.message;

import com.github.seratch.jslack.api.rtm.message.PresenceQuery;
import com.github.seratch.jslack.common.json.GsonFactory;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PresenceQueryTest {

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"presence_query\",\n" +
                "    \"ids\": [\n" +
                "        \"U061F7AUR\",\n" +
                "        \"W123456\"\n" +
                "    ]\n" +
                "}";
        PresenceQuery q = GsonFactory.createSnakeCase().fromJson(json, PresenceQuery.class);
        assertThat(q.getType(), is("presence_query"));
        assertThat(q.getIds(), is(Arrays.asList("U061F7AUR", "W123456")));
    }

    @Test
    public void serialize() {
        String generatedJson = PresenceQuery.builder().ids(Arrays.asList("1", "2")).build().toJSONString();
        String expectedJson = "{\"type\":\"presence_query\",\"ids\":[\"1\",\"2\"]}";
        assertThat(generatedJson, is(expectedJson));
    }

}
