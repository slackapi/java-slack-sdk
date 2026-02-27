package test_locally.api.model.work_objects;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.slack.api.model.work_objects.ExternalUser;
import com.slack.api.model.work_objects.SlackUser;
import com.slack.api.model.work_objects.User;
import test_locally.unit.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class UserTest {
    final Gson gson = GsonFactory.createSnakeCaseWithRequiredPropertyDetection();

    @Test
    public void parseUser_withoutUserType_throwsException() {
        String json = "{\"user_id\": \"U1234568\"}";
        assertThrows(JsonParseException.class, () -> gson.fromJson(json, SlackUser.class));
    }

    @Test
    public void parseUser_withInvalidUserType_throwsException() {
        String json = "{\"user_type\": \"test\"}";
        assertThrows(JsonParseException.class, () -> gson.fromJson(json, SlackUser.class));
    }

    @Test
    public void parseExternalUser_withoutText_throwsException() {
        String json = "{\"user_type\": \"external\"}";
        JsonParseException e = assertThrows(JsonParseException.class, () -> gson.fromJson(json, ExternalUser.class));
        assertThat(e.getMessage(), equalToIgnoringCase("Required field 'text' failed validation in ExternalUser using predicate IsNotNullFieldPredicate"));
    }

    @Test
    public void parseSlackUser_withoutUserId_throwsException() {
        String json = "{\"user_type\": \"slack\"}";
        JsonParseException e = assertThrows(JsonParseException.class, () -> gson.fromJson(json, SlackUser.class));
        assertThat(e.getMessage(), equalToIgnoringCase("Required field 'userId' failed validation in SlackUser using predicate IsValidSlackUserIdPredicate"));
    }

    @Test
    public void parseSlackUser_withInvalidUserId_throwsException() {
        String json = "{\"user_type\": \"slack\", \"user_id\": \"test\"}";
        JsonParseException e = assertThrows(JsonParseException.class, () -> gson.fromJson(json, SlackUser.class));
        assertThat(e.getMessage(), equalToIgnoringCase("Required field 'userId' failed validation in SlackUser using predicate IsValidSlackUserIdPredicate"));
    }

    @Test
    public void parseUsers_createsCorrectUserType() {
        String slackUserJson = "{\"user_type\": \"slack\", \"user_id\": \"U12345678\"}";
        assertThat(gson.fromJson(slackUserJson, User.class), instanceOf(SlackUser.class));

        String externalUserJson = "{\"user_type\":\"external\", \"text\": \"test\"}";
        assertThat(gson.fromJson(externalUserJson, User.class), instanceOf(ExternalUser.class));
    }

    @Test
    public void parseUsers_withFailurePropertiesOff_returnsUnknownUser() {
        Gson lenientGson = GsonFactory.createSnakeCase(false, false, false);
        String badJson = "{\"user_type\": \"something we dont know\"}";
        User user = lenientGson.fromJson(badJson, User.class);
        assertThat(user.getUserType(), is("something we dont know"));
    }

    @Test
    public void testUserBuilders() {
         User slackUser = SlackUser.builder().userId("U12345678").userType("this will get ignored").build();
         User externalUser = ExternalUser.builder().text("test").userType("this will also get ignored").build();
         assertTrue(slackUser.isSlackUser());
         assertFalse(slackUser.isExternalUser());
         assertTrue(externalUser.isExternalUser());
         assertFalse(externalUser.isSlackUser());
         assertThat(slackUser.getUserType(), is("slack"));
         assertThat(externalUser.getUserType(), is("external"));
    }
}
