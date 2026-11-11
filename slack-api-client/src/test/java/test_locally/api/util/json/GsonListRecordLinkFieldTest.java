package test_locally.api.util.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.slack.api.methods.response.slack_lists.SlackListsItemsListResponse;
import com.slack.api.model.list.ListRecord;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GsonListRecordLinkFieldTest {

    private final Gson gson = GsonFactory.createSnakeCase();

    @Test
    public void deserializeCamelCaseLinkField() {
        String json = "{\"ok\":true,\"items\":[{\"fields\":[{" +
                "\"link\":[{" +
                "\"originalUrl\":\"https://github.com/org/repo/pull/7\"," +
                "\"displayAsUrl\":false," +
                "\"displayName\":\"repo#7\"" +
                "}]}]}]}";

        SlackListsItemsListResponse response = gson.fromJson(json, SlackListsItemsListResponse.class);
        ListRecord.LinkField linkField = response.getItems().get(0).getFields().get(0).getLink().get(0);

        assertThat(linkField.getOriginalUrl(), is("https://github.com/org/repo/pull/7"));
        assertThat(linkField.getDisplayAsUrl(), is(false));
        assertThat(linkField.getDisplayName(), is("repo#7"));
    }

    @Test
    public void serializeLinkFieldAsSnakeCase() {
        ListRecord.LinkField linkField = ListRecord.LinkField.builder()
                .originalUrl("https://github.com/org/repo/pull/7")
                .displayAsUrl(false)
                .displayName("repo#7")
                .build();

        JsonObject json = gson.toJsonTree(linkField).getAsJsonObject();

        assertThat(json.get("original_url").getAsString(), is("https://github.com/org/repo/pull/7"));
        assertThat(json.get("display_as_url").getAsBoolean(), is(false));
        assertThat(json.get("display_name").getAsString(), is("repo#7"));
        assertThat(json.has("originalUrl"), is(false));
        assertThat(json.has("displayAsUrl"), is(false));
        assertThat(json.has("displayName"), is(false));
    }
}
