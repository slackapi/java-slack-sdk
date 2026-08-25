package test_locally.api.model.list;

import com.google.gson.Gson;
import com.slack.api.model.list.ListRecord;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ListRecordFieldTest {

    private final Gson gson = GsonFactory.createSnakeCase();

    // The Lists message field is an array of message references. Verified against the live
    // API (slackLists.items.list and the conversations.replies/history nested list_record
    // path both return List<{value, channel_id, ts, thread_ts?}>). thread_ts is present only
    // for threaded replies. There is no single-object form on these endpoints.

    @Test
    public void messageArray() {
        String json = "{\"id\":\"r1\",\"fields\":[{" +
            "\"key\":\"k1\",\"message\":[" +
                "{\"value\":\"https://example.slack.com/archives/C1/p1\",\"channel_id\":\"C1\",\"ts\":\"1.0\"}," +
                "{\"value\":\"https://example.slack.com/archives/C1/p2?thread_ts=0.5\",\"channel_id\":\"C1\",\"ts\":\"2.0\",\"thread_ts\":\"0.5\"}" +
            "]}]}";
        ListRecord record = gson.fromJson(json, ListRecord.class);
        ListRecord.Field field = record.getFields().get(0);

        assertThat(field.getMessage(), is(notNullValue()));
        assertThat(field.getMessage().size(), is(2));
        assertThat(field.getMessage().get(0).getValue(), is("https://example.slack.com/archives/C1/p1"));
        assertThat(field.getMessage().get(0).getChannelId(), is("C1"));
        assertThat(field.getMessage().get(0).getTs(), is("1.0"));
        assertThat(field.getMessage().get(0).getThreadTs(), is(nullValue()));
        // thread_ts is populated only for a threaded reply reference
        assertThat(field.getMessage().get(1).getThreadTs(), is("0.5"));
    }

    @Test
    public void messageAbsent() {
        String json = "{\"id\":\"r3\",\"fields\":[{\"key\":\"k1\",\"number\":[1.0]}]}";
        ListRecord record = gson.fromJson(json, ListRecord.class);
        ListRecord.Field field = record.getFields().get(0);

        assertThat(field.getMessage(), is(nullValue()));
    }

    @Test
    public void messageEmptyArray() {
        String json = "{\"id\":\"r5\",\"fields\":[{\"key\":\"k1\",\"message\":[]}]}";
        ListRecord record = gson.fromJson(json, ListRecord.class);
        ListRecord.Field field = record.getFields().get(0);

        assertThat(field.getMessage(), is(notNullValue()));
        assertThat(field.getMessage().size(), is(0));
    }

    @Test
    public void roundTrip() {
        String json = "{\"id\":\"r6\",\"fields\":[{\"key\":\"k1\",\"message\":[" +
            "{\"value\":\"https://example.slack.com/archives/C1/p1\",\"channel_id\":\"C1\",\"ts\":\"1.0\"}]}]}";
        ListRecord record = gson.fromJson(json, ListRecord.class);
        String output = gson.toJson(record);

        ListRecord reparsed = gson.fromJson(output, ListRecord.class);
        assertThat(reparsed.getFields().get(0).getMessage().get(0).getValue(),
                is("https://example.slack.com/archives/C1/p1"));
    }

    @Test
    public void builder() {
        ListRecord.MessageRef ref = ListRecord.MessageRef.builder()
                .value("https://example.slack.com/archives/C1/p1")
                .channelId("C1")
                .ts("1.0")
                .build();

        ListRecord.Field field = ListRecord.Field.builder()
                .key("k1")
                .message(java.util.Collections.singletonList(ref))
                .build();

        assertThat(field.getMessage().get(0).getValue(), is("https://example.slack.com/archives/C1/p1"));
    }
}
