package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.request.usergroups.users.UsergroupsUsersListRequest;
import com.slack.api.methods.response.usergroups.*;
import com.slack.api.methods.response.usergroups.users.UsergroupsUsersListResponse;
import com.slack.api.methods.response.usergroups.users.UsergroupsUsersUpdateResponse;
import com.slack.api.methods.response.users.UsersListResponse;
import com.slack.api.model.User;
import com.slack.api.model.Usergroup;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class usergroups_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("usergroups.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

    @Test
    public void create() throws Exception {
        String usergroupName = "usergroup-" + System.currentTimeMillis();
        UsergroupsCreateResponse response = slack.methods().usergroupsCreate(r -> r
                .token(userToken)
                .name(usergroupName));
        if (response.isOk()) {
            assertThat(response.getUsergroup(), is(notNullValue()));
            assertThat(response.getUsergroup().getName(), is(usergroupName));
        } else {
            assertThat(response.getError(), is(anyOf(
                    // For a good old token, "paid_teams_only" can be returned as the error
                    equalTo("paid_teams_only"),
                    // As of 2018, this code is generally returned for newly created token
                    equalTo("missing_scope")
            )));
        }
    }

    @Test
    public void list() throws Exception {
        UsergroupsListResponse response = slack.methods().usergroupsList(r -> r.token(botToken)
                .includeUsers(true)
                .includeCount(true)
                .includeDisabled(true)
        );
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void list_async() throws Exception {
        UsergroupsListResponse response = slack.methodsAsync().usergroupsList(r -> r.token(botToken)).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void usergroups() throws Exception {
        UsergroupsListResponse usergroups = slack.methods().usergroupsList(r -> r.token(botToken));
        if (usergroups.isOk() && usergroups.getUsergroups().size() > 0) {
            UsergroupsUsersListResponse response = slack.methods().usergroupsUsersList(
                    UsergroupsUsersListRequest.builder()
                            .token(userToken)
                            .includeDisabled(false)
                            .usergroup(usergroups.getUsergroups().get(0).getId())
                            .build());
            assertThat(response.getError(), is(nullValue()));
        }

        UsergroupsCreateResponse creation = slack.methods().usergroupsCreate(r -> r
                .token(userToken)
                .name("usergroup-" + System.currentTimeMillis())
                .description("Something wrong"));
        assertThat(creation.getError(), is(nullValue()));
        final Usergroup usergroup = creation.getUsergroup();
        {
            UsergroupsDisableResponse response = slack.methods().usergroupsDisable(r -> r
                    .token(userToken)
                    .usergroup(usergroup.getId()));
            assertThat(response.getError(), is(nullValue()));
        }
        {
            UsergroupsEnableResponse response = slack.methods().usergroupsEnable(r -> r
                    .token(userToken)
                    .usergroup(usergroup.getId()));
            assertThat(response.getError(), is(nullValue()));
        }
        {
            UsergroupsUpdateResponse response = slack.methods().usergroupsUpdate(r -> r
                    .token(userToken)
                    .usergroup(usergroup.getId())
                    .description("updated"));
            assertThat(response.getError(), is(nullValue()));
        }
        {
            UsersListResponse usersListResponse = slack.methods().usersList(r -> r
                    .token(userToken)
                    .limit(3));
            List<String> userIds = new ArrayList<>();
            for (User member : usersListResponse.getMembers()) {
                if (!member.isDeleted()) {
                    userIds.add(member.getId());
                }
            }
            UsergroupsUsersUpdateResponse response = slack.methods().usergroupsUsersUpdate(r -> r
                    .token(userToken)
                    .usergroup(usergroup.getId())
                    .users(userIds));
            assertThat(response.getError(), is(nullValue()));
        }
    }

    @Test
    public void users_failure() throws Exception {
        UsergroupsUsersListResponse response = slack.methods().usergroupsUsersList(
                UsergroupsUsersListRequest.builder()
                        .token(botToken)
                        .includeDisabled(false)
                        .usergroup("dummy")
                        .build());
        assertThat(response.isOk(), is(false));
//            assertThat(response.getError(), is("missing_required_argument"));
        // As of 2018/05, the error message has been changed
        assertThat(response.getError(), is("no_such_subteam"));
    }

}
