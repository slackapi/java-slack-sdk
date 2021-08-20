package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.LinkSharedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LinkSharedEventTest {

    @Test
    public void typeName() {
        assertThat(LinkSharedEvent.TYPE_NAME, is("link_shared"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "        \"type\": \"link_shared\",\n" +
                "        \"channel\": \"Cxxxxxx\",\n" +
                "        \"user\": \"Uxxxxxxx\",\n" +
                "        \"message_ts\": \"123456789.9875\",\n" +
                "        \"thread_ts\": \"123456621.1855\",\n" +
                "        \"links\": [\n" +
                "            {\n" +
                "                \"domain\": \"example.com\",\n" +
                "                \"url\": \"https://example.com/12345\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"domain\": \"example.com\",\n" +
                "                \"url\": \"https://example.com/67890\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"domain\": \"another-example.com\",\n" +
                "                \"url\": \"https://yet.another-example.com/v/abcde\"\n" +
                "            }\n" +
                "        ]\n" +
                "}";
        LinkSharedEvent event = GsonFactory.createSnakeCase().fromJson(json, LinkSharedEvent.class);
        assertThat(event.getType(), is("link_shared"));
        assertThat(event.getChannel(), is("Cxxxxxx"));
        assertThat(event.getUser(), is("Uxxxxxxx"));
        assertThat(event.getMessageTs(), is("123456789.9875"));
        assertThat(event.getThreadTs(), is("123456621.1855"));
        assertThat(event.getLinks().size(), is(3));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        LinkSharedEvent event = new LinkSharedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"link_shared\",\"is_bot_user_member\":false}";
        assertThat(generatedJson, is(expectedJson));
    }

    @Test
    public void newUnfurls_2021_08() {
        // https://api.slack.com/changelog/2021-08-changes-to-unfurls
        String json = "{\n" +
                "    \"type\": \"link_shared\",\n" +
                "    \"channel\": \"COMPOSER\",\n" +
                "    \"is_bot_user_member\": true,\n" +
                "    \"user\": \"Uxxxxxxx\",\n" +
                "    \"message_ts\": \"Uxxxxxxx-909b5454-75f8-4ac4-b325-1b40e230bbd8-gryl3kb80b3wm49ihzoo35fyqoq08n2y\",\n" +
                "    \"unfurl_id\": \"Uxxxxxxx-909b5454-75f8-4ac4-b325-1b40e230bbd8-gryl3kb80b3wm49ihzoo35fyqoq08n2y\",\n" +
                "    \"source\": \"composer\",\n" +
                "    \"links\": [\n" +
                "        {\n" +
                "            \"domain\": \"example.com\",\n" +
                "            \"url\": \"https://example.com/12345\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"domain\": \"example.com\",\n" +
                "            \"url\": \"https://example.com/67890\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"domain\": \"another-example.com\",\n" +
                "            \"url\": \"https://yet.another-example.com/v/abcde\"\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";
        LinkSharedEvent event = GsonFactory.createSnakeCase().fromJson(json, LinkSharedEvent.class);
        assertThat(event.getType(), is("link_shared"));
    }

}
