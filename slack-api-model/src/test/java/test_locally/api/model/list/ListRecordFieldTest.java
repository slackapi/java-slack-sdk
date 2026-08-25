package test_locally.api.model.list;

import com.google.gson.Gson;
import com.slack.api.model.Message;
import com.slack.api.model.list.ListRecord;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ListRecordFieldTest {

    private final Gson gson = GsonFactory.createSnakeCase();

    @Test
    public void messageAsObject() {
        String json = "{\"id\":\"r1\",\"fields\":[{" +
            "\"key\":\"k1\"," + "\"message\":{\"text\":\"hello\",\"ts\":\"1.0\"}" +
            "}]}";
        ListRecord record = gson.fromJson(json, ListRecord.class);
        ListRecord.Field field = record.getFields().get(0);

        // Old API works
        assertThat(field.getMessage(), is(notNullValue()));
        assertThat(field.getMessage().getText(), is("hello"));

        // New API works
        assertThat(field.getMessages(), is(notNullValue()));
        assertThat(field.getMessages().size(), is(1));

        assertThat(field.getMessages().get(0).getText(), is("hello"));
    }

    @Test
    public void messageAsArray() {
        String json = "{\"id\":\"r2\",\"fields\":[{" +
            "\"key\":\"k1\"," +
            "\"message\":[" +
                " {\"text\":\"first\",\"ts\":\"1.0\"}," +
                " {\"text\":\"second\",\"ts\":\"2.0\"}" +
            "]" +
        "}]}";

        ListRecord record = gson.fromJson(json, ListRecord.class);
        ListRecord.Field field = record.getFields().get(0);

        // Old API returns first
        assertThat(field.getMessage(), is(notNullValue()));
        assertThat(field.getMessage().getText(), is("first"));

        // New API returns all
        assertThat(field.getMessages().size(), is(2));

        assertThat(field.getMessages().get(0).getText(), is("first"));

        assertThat(field.getMessages().get(1).getText(), is("second"));
    }

    @Test
    public void messageAbsent() {
        String json = "{\"id\":\"r3\",\"fields\":[{\"key\":\"k1\",\"number\":[1.0]}]}";
        ListRecord record = gson.fromJson(json, ListRecord.class);
        ListRecord.Field field = record.getFields().get(0);

        assertThat(field.getMessage(), is(nullValue()));
        assertThat(field.getMessages(), is(nullValue()));
    }

    @Test
    public void messageNull() {
        String json = "{\"id\":\"r4\",\"fields\":[{\"key\":\"k1\",\"message\":null}]}";
        ListRecord record = gson.fromJson(json, ListRecord.class);
        ListRecord.Field field = record.getFields().get(0);

        assertThat(field.getMessage(), is(nullValue()));
        assertThat(field.getMessages(), is(nullValue()));
    }

    @Test
    public void messageEmptyArray() {
        String json = "{\"id\":\"r5\",\"fields\":[{\"key\":\"k1\",\"message\":[]}]}";
        ListRecord record = gson.fromJson(json, ListRecord.class);
        ListRecord.Field field = record.getFields().get(0);

        assertThat(field.getMessage(), is(nullValue()));
        assertThat(field.getMessages(), is(notNullValue()));
        assertThat(field.getMessages().size(), is(0));
    }

    @Test
    public void serializeSingleMessage() {
        String json = "{\"id\":\"r6\",\"fields\":[{\"key\":\"k1\",\"message\":{\"text\":\"hi\"}}]}";
        ListRecord record = gson.fromJson(json, ListRecord.class);
        String output = gson.toJson(record);

        // Round-trips cleanly
        ListRecord reparsed = gson.fromJson(output, ListRecord.class);
        assertThat(reparsed.getFields().get(0).getMessage().getText(), is("hi"));
    }

    @Test
    public void builder() {
        Message msg = new Message();
        msg.setText("built");

        ListRecord.Field field = ListRecord.Field.builder()
                .key("k1")
                .message(msg)
                .build();

        assertThat(field.getMessage().getText(), is("built"));
    }
}