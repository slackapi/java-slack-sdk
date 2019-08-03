package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.admin.AdminUsersSessionResetRequest;
import com.github.seratch.jslack.api.methods.response.admin.AdminUsersSessionResetResponse;
import com.github.seratch.jslack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@Slf4j
public class admin_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void usersSessionReset() throws IOException, SlackApiException {
        String userId = null;
        List<User> members = slack.methods(token).usersList(req -> req).getMembers();
        for (User member : members) {
            if (!member.isBot()) {
                userId = member.getId();
                break;
            }
        }
        assertThat(userId, is(notNullValue()));

        AdminUsersSessionResetResponse response = slack.methods(token)
                .adminUsersSessionReset(AdminUsersSessionResetRequest.builder().userId(userId).build());
        assertThat(response.getError(), is("feature_not_enabled"));
        assertThat(response.isOk(), is(false));
    }
}