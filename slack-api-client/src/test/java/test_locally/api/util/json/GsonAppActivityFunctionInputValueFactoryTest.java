package test_locally.api.util.json;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.slack.api.SlackConfig;
import com.slack.api.model.admin.AppActivity;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
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
                "credentialSource=null, " +
                "externalTokenId=null, " +
                "userContextSource=null)"));
    }

    @Test
    public void deserialize_credential() {
        AppActivity.FunctionInputValue value = parse(strictGson(), credential_value);
        assertThat(value.toString(), is("AppActivity.FunctionInputValue(" +
                "stringValue=null, " +
                "credentialSource=END_USER, " +
                "externalTokenId=Et12345ABCDE, " +
                "userContextSource=)"));
    }

    @Test(expected = JsonParseException.class)
    public void deserialize_unknown_key_strict() {
        parse(strictGson(), unknown_key_value);
    }

    @Test
    public void serialize_string() {
        AppActivity.FunctionInputValue value = new AppActivity.FunctionInputValue();
        value.setStringValue("https://www.example.com/");
        assertThat(lenientGson().toJson(value), is("\"https://www.example.com/\""));
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
