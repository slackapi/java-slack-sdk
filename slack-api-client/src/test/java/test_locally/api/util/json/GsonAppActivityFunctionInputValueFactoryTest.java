package test_locally.api.util.json;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.slack.api.SlackConfig;
import com.slack.api.model.admin.AppActivity;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GsonAppActivityFunctionInputValueFactoryTest {

    static Gson lenientGson() {
        return GsonFactory.createSnakeCase();
    }

    static Gson strictGson() {
        SlackConfig config = new SlackConfig();
        config.setFailOnUnknownProperties(true);
        return GsonFactory.createSnakeCase(config);
    }

    static AppActivity.FunctionInputValue parse(Gson gson, String value) {
        String json = "{\"value\": " + value + ", \"hidden\": false, \"locked\": false}";
        return gson.fromJson(json, AppActivity.FunctionInput.class).getValue();
    }

    String string_value = "\"https://www.example.com/\"";

    String credential_value = "{\n" +
            "  \"credential_source\": \"END_USER\",\n" +
            "  \"external_token_id\": \"Et12345ABCDE\",\n" +
            "  \"user_context_source\": \"\"\n" +
            "}";

    String unknown_key_value = "{\n" +
            "  \"credential_source\": \"DEVELOPER\",\n" +
            "  \"unknown\": \"value\"\n" +
            "}";

    @Test
    public void deserialize_string() {
        AppActivity.FunctionInputValue value = parse(strictGson(), string_value);
        assertThat(value.toString(), is("AppActivity.FunctionInputValue(" +
                "stringValue=https://www.example.com/, " +
                "stringValues=null, " +
                "credentialSource=null, " +
                "externalTokenId=null, " +
                "userContextSource=null, " +
                "rawValue=null)"));
    }

    @Test
    public void deserialize_credential() {
        AppActivity.FunctionInputValue value = parse(strictGson(), credential_value);
        assertThat(value.toString(), is("AppActivity.FunctionInputValue(" +
                "stringValue=null, " +
                "stringValues=null, " +
                "credentialSource=END_USER, " +
                "externalTokenId=Et12345ABCDE, " +
                "userContextSource=, " +
                "rawValue=null)"));
    }

    @Test(expected = JsonParseException.class)
    public void deserialize_unknown_key_strict() {
        parse(strictGson(), unknown_key_value);
    }

    @Test
    public void deserialize_unknown_key_lenient() {
        AppActivity.FunctionInputValue value = parse(lenientGson(), unknown_key_value);
        assertThat(value.getCredentialSource(), is("DEVELOPER"));
        assertThat(value.getRawValue(), is(nullValue()));
    }

    @Test
    public void deserialize_array() {
        AppActivity.FunctionInputValue value = parse(lenientGson(), "[\"U12345\", \"U67890\"]");
        assertThat(value.getStringValues(), is(Arrays.asList("U12345", "U67890")));
    }

    @Test
    public void deserialize_mixed_array() {
        AppActivity.FunctionInputValue value = parse(lenientGson(), "[\"U12345\", 1]");
        assertThat(value.getStringValues(), is(nullValue()));
        assertThat(value.getRawValue().toString(), is("[U12345, 1.0]"));
    }

    @Test
    public void deserialize_credential_object_value() {
        AppActivity.FunctionInputValue value = parse(lenientGson(), "{\"credential_source\": {\"type\": \"END_USER\"}}");
        assertThat(value.getCredentialSource(), is(nullValue()));
        assertThat(value.getRawValue().toString(), is("{credential_source={type=END_USER}}"));
    }

    @Test
    public void deserialize_boolean() {
        AppActivity.FunctionInputValue value = parse(lenientGson(), "true");
        assertThat(value.getStringValue(), is(nullValue()));
        assertThat(value.getRawValue(), is(true));
    }

    @Test
    public void deserialize_object() {
        AppActivity.FunctionInputValue value = parse(lenientGson(), "{\"user_id\": \"U12345\"}");
        assertThat(value.getCredentialSource(), is(nullValue()));
        assertThat(value.getRawValue().toString(), is("{user_id=U12345}"));
    }

    @Test
    public void serialize_empty() {
        assertThat(lenientGson().toJson(new AppActivity.FunctionInputValue()), is("null"));
    }

    @Test
    public void serialize_string() {
        AppActivity.FunctionInputValue value = new AppActivity.FunctionInputValue();
        value.setStringValue("https://www.example.com/");
        assertThat(lenientGson().toJson(value), is("\"https://www.example.com/\""));
    }

    @Test
    public void serialize_string_values() {
        AppActivity.FunctionInputValue value = new AppActivity.FunctionInputValue();
        value.setStringValues(Arrays.asList("U12345", "U67890"));
        assertThat(lenientGson().toJson(value), is("[\"U12345\",\"U67890\"]"));
    }

    @Test
    public void serialize_raw_value() {
        AppActivity.FunctionInputValue value = new AppActivity.FunctionInputValue();
        value.setRawValue(true);
        assertThat(lenientGson().toJson(value), is("true"));
    }

    @Test
    public void serialize_credential() {
        AppActivity.FunctionInputValue value = new AppActivity.FunctionInputValue();
        value.setCredentialSource("END_USER");
        value.setExternalTokenId("Et12345ABCDE");
        assertThat(lenientGson().toJson(value), is("{" +
                "\"credential_source\":\"END_USER\"," +
                "\"external_token_id\":\"Et12345ABCDE\"" +
                "}"));
    }

}
