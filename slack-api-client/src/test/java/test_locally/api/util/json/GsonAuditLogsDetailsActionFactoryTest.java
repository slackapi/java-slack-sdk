package test_locally.api.util.json;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.slack.api.SlackConfig;
import com.slack.api.audit.response.LogsResponse;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GsonAuditLogsDetailsActionFactoryTest {

    static Gson lenientGson() {
        return GsonFactory.createSnakeCase();
    }

    static Gson strictGson() {
        SlackConfig config = new SlackConfig();
        config.setFailOnUnknownProperties(true);
        return GsonFactory.createSnakeCase(config);
    }

    static String logsJson(String action) {
        return "{\n" +
                "  \"entries\": [\n" +
                "    {\n" +
                "      \"id\": \"1df9d306-f268-49ae-8680-b0c6241f14f3\",\n" +
                "      \"date_create\": 1595748462,\n" +
                "      \"action\": \"app_allowlist_rule_created\",\n" +
                "      \"details\": {\n" +
                "        \"action\": " + action + "\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    static LogsResponse.DetailsAction parse(Gson gson, String action) {
        LogsResponse response = gson.fromJson(logsJson(action), LogsResponse.class);
        return response.getEntries().get(0).getDetails().getAction();
    }

    String string_action = "\"update\"";

    String object_action = "{\n" +
            "  \"resolution\": {\n" +
            "    \"value\": \"restrict\"\n" +
            "  }\n" +
            "}";

    String array_action = "{\n" +
            "  \"resolution\": {\n" +
            "    \"value\": \"restrict\"\n" +
            "  },\n" +
            "  \"notify\": [\n" +
            "    {\n" +
            "      \"entity_type\": \"user\",\n" +
            "      \"values\": [\n" +
            "        \"W111\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    String array_with_string_item_action = "{\n" +
            "  \"notify\": [\n" +
            "    \"W111\",\n" +
            "    {\n" +
            "      \"entity_type\": \"user\",\n" +
            "      \"values\": [\n" +
            "        \"W222\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    String unknown_key_action = "{\n" +
            "  \"resolution\": {\n" +
            "    \"value\": \"restrict\"\n" +
            "  },\n" +
            "  \"unknown\": \"value\"\n" +
            "}";

    @Test
    public void deserialize_string() {
        LogsResponse.DetailsAction action = parse(strictGson(), string_action);
        assertThat(action.toString(), is("LogsResponse.DetailsAction(" +
                "stringValue=update, " +
                "resolution=null, " +
                "notify=null)"));
    }

    @Test
    public void deserialize_object() {
        LogsResponse.DetailsAction action = parse(strictGson(), object_action);
        assertThat(action.toString(), is("LogsResponse.DetailsAction(" +
                "stringValue=null, " +
                "resolution=LogsResponse.AAARuleActionResolution(value=restrict), " +
                "notify=null)"));
    }

    @Test
    public void deserialize_array() {
        LogsResponse.DetailsAction action = parse(strictGson(), array_action);
        assertThat(action.toString(), is("LogsResponse.DetailsAction(" +
                "stringValue=null, " +
                "resolution=LogsResponse.AAARuleActionResolution(value=restrict), " +
                "notify=[LogsResponse.AAARuleActionNotify(entityType=user, values=[W111])])"));
    }

    @Test(expected = JsonParseException.class)
    public void deserialize_array_non_object_item_strict() {
        parse(strictGson(), array_with_string_item_action);
    }

    @Test
    public void deserialize_array_non_object_item_lenient() {
        LogsResponse.DetailsAction action = parse(lenientGson(), array_with_string_item_action);
        assertThat(action.toString(), is("LogsResponse.DetailsAction(" +
                "stringValue=null, " +
                "resolution=null, " +
                "notify=[LogsResponse.AAARuleActionNotify(entityType=user, values=[W222])])"));
    }

    @Test(expected = JsonParseException.class)
    public void deserialize_unknown_key_strict() {
        parse(strictGson(), unknown_key_action);
    }

    @Test
    public void deserialize_unknown_key_lenient() {
        LogsResponse.DetailsAction action = parse(lenientGson(), unknown_key_action);
        assertThat(action.toString(), is("LogsResponse.DetailsAction(" +
                "stringValue=null, " +
                "resolution=LogsResponse.AAARuleActionResolution(value=restrict), " +
                "notify=null)"));
    }

    @Test(expected = JsonParseException.class)
    public void deserialize_top_level_array_strict() {
        parse(strictGson(), "[\"update\"]");
    }

    @Test
    public void deserialize_top_level_array_lenient() {
        LogsResponse.DetailsAction action = parse(lenientGson(), "[\"update\"]");
        assertThat(action.getStringValue(), is(nullValue()));
        assertThat(action.getResolution(), is(nullValue()));
        assertThat(action.getNotify(), is(nullValue()));
    }

    @Test
    public void serialize_string() {
        Gson gson = GsonFactory.createSnakeCase();
        LogsResponse.DetailsAction action = new LogsResponse.DetailsAction();
        action.setStringValue("update");
        assertThat(gson.toJson(action), is("\"update\""));
    }

    @Test
    public void serialize_object() {
        Gson gson = GsonFactory.createSnakeCase();
        LogsResponse.DetailsAction action = new LogsResponse.DetailsAction();
        LogsResponse.AAARuleActionResolution resolution = new LogsResponse.AAARuleActionResolution();
        resolution.setValue("restrict");
        action.setResolution(resolution);
        LogsResponse.AAARuleActionNotify notify = new LogsResponse.AAARuleActionNotify();
        notify.setEntityType("user");
        notify.setValues(Arrays.asList("W111"));
        action.setNotify(Arrays.asList(notify));
        assertThat(gson.toJson(action), is("{" +
                "\"resolution\":{\"value\":\"restrict\"}," +
                "\"notify\":[{\"entity_type\":\"user\",\"values\":[\"W111\"]}]" +
                "}"));
    }

}
