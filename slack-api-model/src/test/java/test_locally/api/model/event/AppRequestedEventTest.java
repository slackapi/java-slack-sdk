package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.admin.AppRequest;
import com.slack.api.model.event.AppRequestedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppRequestedEventTest {

    @Test
    public void typeName() {
        assertThat(AppRequestedEvent.TYPE_NAME, is("app_requested"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "  \"type\": \"app_requested\",\n" +
                "  \"app_request\":{\n" +
                "      \"id\": \"1234\",\n" +
                "      \"app\": {\n" +
                "        \"id\": \"A5678\",\n" +
                "        \"name\": \"Brent's app\",\n" +
                "        \"description\": \"They're good apps, Bront.\",\n" +
                "        \"help_url\": \"brontsapp.com\",\n" +
                "        \"privacy_policy_url\": \"brontsapp.com\",\n" +
                "        \"app_homepage_url\": \"brontsapp.com\",\n" +
                "        \"app_directory_url\": \"https://slack.slack.com/apps/A102ARD7Y\",\n" +
                "        \"is_app_directory_approved\": true,\n" +
                "        \"is_internal\": false,\n" +
                "        \"additional_info\": \"none\"\n" +
                "      },\n" +
                "      \"previous_resolution\": {\n" +
                "         \"status\": \"approved\",\n" +
                "         \"scopes\": [\n" +
                "          {\n" +
                "            \"name\": \"app_requested\",\n" +
                "            \"description\": \"allows this app to listen for app install requests\",\n" +
                "            \"is_sensitive\": false,\n" +
                "            \"token_type\": \"user\"\n" +
                "          }]\n" +
                "      },\n" +
                "      \"user\":{\n" +
                "        \"id\": \"U1234\",\n" +
                "        \"name\": \"Bront\",\n" +
                "        \"email\": \"bront@brent.com\"\n" +
                "      },\n" +
                "      \"team\": {\n" +
                "        \"id\": \"T1234\",\n" +
                "        \"name\": \"Brant App Team\",\n" +
                "        \"domain\": \"brantappteam\"\n" +
                "      },\n" +
                "      \"scopes\": [\n" +
                "        {\n" +
                "          \"name\": \"app_requested\",\n" +
                "          \"description\": \"allows this app to listen for app install requests\",\n" +
                "          \"is_sensitive\": false,\n" +
                "          \"token_type\": \"user\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"message\": \"none\"\n" +
                "  }\n" +
                "}";
        AppRequestedEvent event = GsonFactory.createSnakeCase().fromJson(json, AppRequestedEvent.class);
        assertThat(event.getType(), is("app_requested"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        AppRequestedEvent event = new AppRequestedEvent();
        AppRequest appRequest = new AppRequest();
        appRequest.setId("xxx");
        event.setAppRequest(appRequest);
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"app_requested\",\"app_request\":{\"id\":\"xxx\"}}";
        assertThat(generatedJson, is(expectedJson));
    }

}
