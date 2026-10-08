package test_locally.api.util.json;

import com.google.gson.Gson;
import com.slack.api.methods.response.conversations.ConversationsRepliesResponse;
import com.slack.api.model.list.ListRecord;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GsonListRecordFieldFactoryTest {

    private final Gson gson = GsonFactory.createSnakeCase();

    private static String repliesWithListRecordFieldMessage(String messageJson) {
        return "{\n" +
                "  \"ok\": true,\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"type\": \"message\",\n" +
                "      \"subtype\": \"bot_message\",\n" +
                "      \"ts\": \"1700000001.000200\",\n" +
                "      \"text\": \"Request has been marked Completed.\",\n" +
                "      \"attachments\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"list_record\": {\n" +
                "            \"record\": {\n" +
                "              \"fields\": [\n" +
                "                {\n" +
                "                  \"key\": \"Col1\",\n" +
                "                  \"column_id\": \"Col1\",\n" +
                "                  \"value\": \"https://example.com\",\n" +
                "                  \"message\": " + messageJson + "\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    private ListRecord.Field firstField(String json) {
        ConversationsRepliesResponse response = gson.fromJson(json, ConversationsRepliesResponse.class);
        return response.getMessages().get(0).getAttachments().get(0)
                .getListRecord().getRecord().getFields().get(0);
    }

    @Test
    public void conversationsReplies_messageAsArray() {
        ListRecord.Field field = firstField(repliesWithListRecordFieldMessage(
                "[{\"value\": \"https://example.com\", \"channel_id\": \"C111\", \"ts\": \"1700000000.000100\"}," +
                        " {\"value\": \"https://example.com/2\", \"channel_id\": \"C111\", \"ts\": \"1700000000.000300\"}]"));

        assertThat(field.getMessages().size(), is(2));
        assertThat(field.getMessages().get(1).getTs(), is("1700000000.000300"));
        assertThat(field.getMessage().getTs(), is("1700000000.000100"));
    }

    @Test
    public void conversationsReplies_messageAsObject() {
        ListRecord.Field field = firstField(repliesWithListRecordFieldMessage(
                "{\"text\": \"hello\", \"ts\": \"1700000000.000100\"}"));

        assertThat(field.getMessage().getText(), is("hello"));
        assertThat(field.getMessages().size(), is(1));
    }
}
