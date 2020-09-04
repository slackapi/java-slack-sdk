package test_locally.api.util.json;

import com.google.gson.Gson;
import com.slack.api.audit.response.LogsResponse;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GsonAuditLogsDetailsChangedValueFactoryTest {

    String array_json = "{\n" +
            "  \"entries\": [\n" +
            "    {\n" +
            "      \"id\": \"1df9d306-f268-49ae-8680-b0c6241f14f3\",\n" +
            "      \"date_create\": 1595748462,\n" +
            "      \"action\": \"pref.enterprise_default_channels\",\n" +
            "      \"actor\": {\n" +
            "        \"type\": \"user\",\n" +
            "        \"user\": {\n" +
            "          \"id\": \"W111\",\n" +
            "          \"name\": \"Alice\",\n" +
            "          \"email\": \"foo@example.com\",\n" +
            "          \"team\": \"T111\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"entity\": {\n" +
            "        \"type\": \"workspace\",\n" +
            "        \"workspace\": {\n" +
            "          \"id\": \"T222\",\n" +
            "          \"name\": \"Test Workspace\",\n" +
            "          \"domain\": \"ws-domain\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"context\": {\n" +
            "        \"location\": {\n" +
            "          \"type\": \"workspace\",\n" +
            "          \"id\": \"T222\",\n" +
            "          \"name\": \"Test Workspace\",\n" +
            "          \"domain\": \"ws-domain\"\n" +
            "        },\n" +
            "        \"ua\": \"\",\n" +
            "        \"ip_address\": \"127.0.0.1\"\n" +
            "      },\n" +
            "      \"details\": {\n" +
            "        \"new_value\": [\n" +
            "          \"C111\"\n" +
            "        ]\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"abb8bb0d-9c67-43ed-813d-9fd1fa86e198\",\n" +
            "      \"date_create\": 1595748461,\n" +
            "      \"action\": \"pref.enterprise_default_channels\",\n" +
            "      \"actor\": {\n" +
            "        \"type\": \"user\",\n" +
            "        \"user\": {\n" +
            "          \"id\": \"W111\",\n" +
            "          \"name\": \"Alice\",\n" +
            "          \"email\": \"foo@example.com\",\n" +
            "          \"team\": \"T111\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"entity\": {\n" +
            "        \"type\": \"enterprise\",\n" +
            "        \"enterprise\": {\n" +
            "          \"id\": \"E111\",\n" +
            "          \"name\": \"Org Name\",\n" +
            "          \"domain\": \"org-domain\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"context\": {\n" +
            "        \"location\": {\n" +
            "          \"type\": \"enterprise\",\n" +
            "          \"id\": \"E111\",\n" +
            "          \"name\": \"Org Name\",\n" +
            "          \"domain\": \"org-domain\"\n" +
            "        },\n" +
            "        \"ua\": \"xxx\",\n" +
            "        \"ip_address\": \"1.1.1.1\"\n" +
            "      },\n" +
            "      \"details\": {\n" +
            "        \"new_value\": [\n" +
            "          \"C111\"\n" +
            "        ],\n" +
            "        \"previous_value\": []\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"response_metadata\": {\n" +
            "    \"next_cursor\": \"\"\n" +
            "  }\n" +
            "}";

    String key_value_json = "{\n" +
            "  \"entries\": [\n" +
            "    {\n" +
            "      \"id\": \"989a4b83-f447-4cf9-b64c-8baa3731418d\",\n" +
            "      \"date_create\": 1595748397,\n" +
            "      \"action\": \"pref.who_can_manage_ext_shared_channels\",\n" +
            "      \"actor\": {\n" +
            "        \"type\": \"user\",\n" +
            "        \"user\": {\n" +
            "          \"id\": \"W111\",\n" +
            "          \"name\": \"Alice\",\n" +
            "          \"email\": \"foo@example.com\",\n" +
            "          \"team\": \"T111\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"entity\": {\n" +
            "        \"type\": \"enterprise\",\n" +
            "        \"enterprise\": {\n" +
            "          \"id\": \"E111\",\n" +
            "          \"name\": \"Org Name\",\n" +
            "          \"domain\": \"org-domain\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"context\": {\n" +
            "        \"location\": {\n" +
            "          \"type\": \"enterprise\",\n" +
            "          \"id\": \"E111\",\n" +
            "          \"name\": \"Org Name\",\n" +
            "          \"domain\": \"org-domain\"\n" +
            "        },\n" +
            "        \"ua\": \"xxx\",\n" +
            "        \"ip_address\": \"1.1.1.1\"\n" +
            "      },\n" +
            "      \"details\": {\n" +
            "        \"new_value\": {\n" +
            "          \"type\": [\n" +
            "            \"TOPLEVEL_ADMINS_AND_OWNERS_AND_SELECTED\"\n" +
            "          ]\n" +
            "        },\n" +
            "        \"previous_value\": {\n" +
            "          \"type\": [\n" +
            "            \"ORG_ADMINS_AND_OWNERS\"\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"1b2c5e5e-1d1c-40fb-8f8f-31250574f131\",\n" +
            "      \"date_create\": 1595748397,\n" +
            "      \"action\": \"pref.who_can_manage_ext_shared_channels\",\n" +
            "      \"actor\": {\n" +
            "        \"type\": \"user\",\n" +
            "        \"user\": {\n" +
            "          \"id\": \"W111\",\n" +
            "          \"name\": \"Alice\",\n" +
            "          \"email\": \"foo@example.com\",\n" +
            "          \"team\": \"T111\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"entity\": {\n" +
            "        \"type\": \"enterprise\",\n" +
            "        \"enterprise\": {\n" +
            "          \"id\": \"E111\",\n" +
            "          \"name\": \"Org Name\",\n" +
            "          \"domain\": \"org-domain\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"context\": {\n" +
            "        \"location\": {\n" +
            "          \"type\": \"enterprise\",\n" +
            "          \"id\": \"E111\",\n" +
            "          \"name\": \"Org Name\",\n" +
            "          \"domain\": \"org-domain\"\n" +
            "        },\n" +
            "        \"ua\": \"xxx\",\n" +
            "        \"ip_address\": \"1.1.1.1\"\n" +
            "      },\n" +
            "      \"details\": {\n" +
            "        \"new_value\": {\n" +
            "          \"type\": [\n" +
            "            \"TOPLEVEL_ADMINS_AND_OWNERS_AND_SELECTED\"\n" +
            "          ]\n" +
            "        },\n" +
            "        \"previous_value\": {\n" +
            "          \"type\": [\n" +
            "            \"ORG_ADMINS_AND_OWNERS\"\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"response_metadata\": {\n" +
            "    \"next_cursor\": \"\"\n" +
            "  }\n" +
            "}";

    @Test
    public void deserialize_array() {
        LogsResponse response = GsonFactory.createSnakeCase().fromJson(array_json, LogsResponse.class);
        assertThat(response.getEntries().size(), is(2));
        assertThat(response.getEntries().get(0).getDetails().getNewValue().toString(),
                is("LogsResponse.DetailsChangedValue(" +
                        "stringValues=[C111], " +
                        "namedStringValues=null)"));
    }

    @Test
    public void deserialize_object() {
        LogsResponse response = GsonFactory.createSnakeCase().fromJson(key_value_json, LogsResponse.class);
        assertThat(response.getEntries().size(), is(2));
        assertThat(response.getEntries().get(0).getDetails().getNewValue().toString(),
                is("LogsResponse.DetailsChangedValue(" +
                        "stringValues=null, " +
                        "namedStringValues={type=[TOPLEVEL_ADMINS_AND_OWNERS_AND_SELECTED]})"));
    }

    @Test
    public void serialize_array() {
        Gson gson = GsonFactory.createSnakeCase();
        LogsResponse response = new LogsResponse();
        response.setRawBody("raw");
        LogsResponse.Entry entry = new LogsResponse.Entry();
        LogsResponse.Details details = new LogsResponse.Details();
        LogsResponse.DetailsChangedValue changedValue = new LogsResponse.DetailsChangedValue();
        changedValue.setStringValues(Arrays.asList("C111", "C222"));
        details.setNewValue(changedValue);
        details.setPreviousValue(changedValue);
        entry.setDetails(details);
        List<LogsResponse.Entry> entries = Arrays.asList(entry);
        response.setEntries(entries);
        String json = gson.toJson(response);
        assertThat(json, is("{\"ok\":false,\"entries\":[{\"details\":{" +
                "\"new_value\":[\"C111\",\"C222\"]," +
                "\"previous_value\":[\"C111\",\"C222\"]" +
                "}}]}"));
    }

    @Test
    public void serialize_object() {
        Gson gson = GsonFactory.createSnakeCase();
        LogsResponse response = new LogsResponse();
        response.setRawBody("raw");
        LogsResponse.Entry entry = new LogsResponse.Entry();
        LogsResponse.Details details = new LogsResponse.Details();
        LogsResponse.DetailsChangedValue changedValue = new LogsResponse.DetailsChangedValue();
        Map<String, List<String>> namedValues = new HashMap<>();
        namedValues.put("type", Arrays.asList("TOPLEVEL_ADMINS_AND_OWNERS_AND_SELECTED"));
        changedValue.setNamedStringValues(namedValues);
        details.setNewValue(changedValue);
        details.setPreviousValue(changedValue);
        entry.setDetails(details);
        List<LogsResponse.Entry> entries = Arrays.asList(entry);
        response.setEntries(entries);
        String json = gson.toJson(response);
        assertThat(json, is("{\"ok\":false,\"entries\":[{\"details\":{" +
                "\"new_value\":{\"type\":[\"TOPLEVEL_ADMINS_AND_OWNERS_AND_SELECTED\"]}," +
                "\"previous_value\":{\"type\":[\"TOPLEVEL_ADMINS_AND_OWNERS_AND_SELECTED\"]}" +
                "}}]}"));
    }

}