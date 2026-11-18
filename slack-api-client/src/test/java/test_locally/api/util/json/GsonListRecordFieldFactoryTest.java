package test_locally.api.util.json;

import com.google.gson.Gson;
import com.slack.api.methods.response.conversations.ConversationsRepliesResponse;
import com.slack.api.model.list.ListRecord;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GsonListRecordFieldFactoryTest {

    private final Gson gson = GsonFactory.createSnakeCase();

    @Test
    public void conversationsRepliesWithListRecordMessageArray() {
        String json = "{\"ok\":true,\"messages\":[{\"type\":\"message\",\"user\":\"U1\",\"ts\":\"1.0\"," +
                "\"text\":\"hi\",\"attachments\":[{\"list_record\":{\"record\":{\"fields\":[" +
                "{\"key\":\"name\",\"message\":{\"text\":\"ok\"}}," +
                "{\"key\":\"details\",\"message\":[{\"text\":\"a\"},{\"text\":\"b\"}]}" +
                "]}}}]}]}";

        ConversationsRepliesResponse response = gson.fromJson(json, ConversationsRepliesResponse.class);
        List<ListRecord.Field> fields = response.getMessages().get(0).getAttachments().get(0)
                .getListRecord().getRecord().getFields();

        assertThat(fields.get(0).getMessage().getText(), is("ok"));
        assertThat(fields.get(1).getMessage().getText(), is("a"));
        assertThat(fields.get(1).getMessages().size(), is(2));
        assertThat(fields.get(1).getMessages().get(1).getText(), is("b"));
    }

    @Test
    public void deserializeMessageArray() {
        String json = "{\"key\":\"Col1\",\"column_id\":\"Col1\",\"message\":[" +
                "{\"value\":\"https://example.slack.com/archives/C111/p1700000000000100\",\"channel_id\":\"C111\",\"ts\":\"1700000000.000100\"}," +
                "{\"value\":\"https://example.slack.com/archives/C111/p1700000000000300\",\"channel_id\":\"C111\",\"ts\":\"1700000000.000300\",\"thread_ts\":\"1700000000.000100\"}" +
                "]}";

        ListRecord.Field field = gson.fromJson(json, ListRecord.Field.class);

        assertThat(field.getMessage().getTs(), is("1700000000.000100"));
        assertThat(field.getMessages().size(), is(2));
        assertThat(field.getMessages().get(1).getTs(), is("1700000000.000300"));
        assertThat(field.getMessages().get(1).getThreadTs(), is("1700000000.000100"));
    }

    @Test
    public void deserializeMessageObject() {
        String json = "{\"key\":\"Col1\",\"column_id\":\"Col1\",\"message\":" +
                "{\"value\":\"https://example.slack.com/archives/C111/p1700000000000100\",\"channel_id\":\"C111\",\"ts\":\"1700000000.000100\"}" +
                "}";

        ListRecord.Field field = gson.fromJson(json, ListRecord.Field.class);

        assertThat(field.getMessage().getTs(), is("1700000000.000100"));
        assertThat(field.getMessages().size(), is(1));
        assertThat(field.getMessages().get(0).getTs(), is("1700000000.000100"));
    }

    @Test
    public void deserializeMessageString() {
        String json = "{\"key\":\"Col1\",\"column_id\":\"Col1\",\"message\":" +
                "\"https://example.slack.com/archives/C111/p1700000000000100\"" +
                "}";

        ListRecord.Field field = gson.fromJson(json, ListRecord.Field.class);

        assertThat(field.getMessage().getPermalink(), is("https://example.slack.com/archives/C111/p1700000000000100"));
        assertThat(field.getMessages().size(), is(1));
        assertThat(field.getMessages().get(0).getPermalink(), is("https://example.slack.com/archives/C111/p1700000000000100"));
    }
}
