package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.request.admin.users.AdminUsersSessionInvalidateRequest;
import com.slack.api.methods.request.admin.users.AdminUsersSessionResetRequest;
import com.slack.api.methods.response.admin.users.*;
import com.slack.api.methods.response.users.UsersListResponse;
import com.slack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApi_users_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String teamAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_ADMIN_USER_TOKEN);
    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static String teamId = System.getenv(Constants.SLACK_SDK_TEST_GRID_TEAM_ID);

    static AsyncMethodsClient methodsAsync = slack.methodsAsync(orgAdminUserToken);

    @Test
    public void usersSessionInvalidate() throws Exception {
        if (teamAdminUserToken != null && orgAdminUserToken != null) {
            String userId = findUserId(Collections.emptyList());
            assertThat(userId, is(notNullValue()));
            AdminUsersSessionInvalidateResponse response = methodsAsync
                    .adminUsersSessionInvalidate(r -> r.sessionId("sessionId").teamId(teamId))
                    .get();
            assertThat(response.getError(), is("invalid_arguments"));
        }
    }

    @Test
    public void usersSessionReset() throws Exception {
        if (teamAdminUserToken != null && orgAdminUserToken != null) {
            String userId = findUserId(Collections.emptyList());
            assertThat(userId, is(notNullValue()));
            AdminUsersSessionResetResponse response = methodsAsync
                    .adminUsersSessionReset(AdminUsersSessionResetRequest.builder().userId(userId).build())
                    .get();
            assertThat(response.getError(), is(nullValue()));
        }
    }

    @Test
    public void usersSessionList() throws Exception {
        if (teamAdminUserToken != null && orgAdminUserToken != null) {
            AdminUsersSessionListResponse response = methodsAsync
                    .adminUsersSessionList(r -> r.limit(5))
                    .get();
            assertThat(response.getError(), is(nullValue()));

            String userId = findUserId(Collections.emptyList());
            assertThat(userId, is(notNullValue()));
            response = methodsAsync
                    .adminUsersSessionList(r -> r.limit(10).teamId(teamId).userId(userId))
                    .get();
            assertThat(response.getError(), is(nullValue()));
        }
    }

    @Test
    public void users() throws Exception {
        if (teamAdminUserToken != null && orgAdminUserToken != null) {
            String teamId = this.teamId;
            AsyncMethodsClient methodsAsync = slack.methodsAsync(orgAdminUserToken);

            AdminUsersListResponse.User user = null;
            String nextCursor = "dummy";
            while (!nextCursor.equals("")) {
                final String cursor = nextCursor.equals("dummy") ? null : nextCursor;
                AdminUsersListResponse users = methodsAsync.adminUsersList(r -> r
                        .limit(100)
                        .cursor(cursor)
                        .teamId(teamId)).get();
                assertThat(users.getError(), is(nullValue()));

                Optional<AdminUsersListResponse.User> maybeUser = users.getUsers().stream()
                        .filter(u -> u.isRestricted() || u.isUltraRestricted()).findFirst();
                if (maybeUser.isPresent()) {
                    user = maybeUser.get();
                    break;
                }
                nextCursor = users.getResponseMetadata().getNextCursor();
            }
            assertThat("Create a guest user for this test", user, is(notNullValue()));
            final String guestUserId = user.getId();
            long defaultExpirationTs = ZonedDateTime.now().toEpochSecond() / 1000;
            // same timestamp results in "failed_to_validate_expiration" error
            final Long expirationTs = user.getExpirationTs() != null && user.getExpirationTs() != 0 ?
                    user.getExpirationTs() + 1 : defaultExpirationTs + 3600;

            AdminUsersSetExpirationResponse response = methodsAsync.adminUsersSetExpiration(r -> r
                    .teamId(teamId)
                    .userId(guestUserId)
                    .expirationTs(expirationTs)
            ).get();
            assertThat(response.getError(), is(nullValue()));
        }
    }

    private String findUserId(List<String> idsToSkip) throws Exception {
        String userId = null;
        UsersListResponse usersListResponse = slack.methodsAsync(teamAdminUserToken).usersList(req -> req).get();
        assertThat(usersListResponse.getError(), is(nullValue()));
        List<User> members = usersListResponse.getMembers();
        for (User member : members) {
            if (member.isBot() || member.isDeleted() || member.isAppUser() || member.isOwner() || member.isStranger()) {
                continue;
            } else {
                if (!idsToSkip.contains(member.getId())) {
                    userId = member.getId();
                    break;
                }
            }
        }
        return userId;
    }

}
