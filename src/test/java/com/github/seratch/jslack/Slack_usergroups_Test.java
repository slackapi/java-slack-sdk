package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.request.usergroups.UsergroupsCreateRequest;
import com.github.seratch.jslack.api.methods.request.usergroups.UsergroupsListRequest;
import com.github.seratch.jslack.api.methods.request.usergroups.users.UsergroupUsersListRequest;
import com.github.seratch.jslack.api.methods.response.usergroups.UsergroupsCreateResponse;
import com.github.seratch.jslack.api.methods.response.usergroups.UsergroupsListResponse;
import com.github.seratch.jslack.api.methods.response.usergroups.users.UsergroupUsersListResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class Slack_usergroups_Test {

    Slack slack = Slack.getInstance();
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void create() throws Exception {
        UsergroupsCreateResponse response = slack.methods().usergroupsCreate(UsergroupsCreateRequest.builder()
                .token(token)
                .name("usergroup-" + System.currentTimeMillis())
                .build());
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is(anyOf(
                // For a good old token, "paid_teams_only" can be returned as the error
                equalTo("paid_teams_only"),
                // As of 2018, this code is generally returned for newly created token
                equalTo("missing_scope")
        )));
    }

    @Test
    public void list() throws Exception {
        UsergroupsListResponse response = slack.methods().usergroupsList(UsergroupsListRequest.builder().token(token).build());
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void users() throws Exception {
        UsergroupsListResponse usergroups = slack.methods().usergroupsList(UsergroupsListRequest.builder().token(token).build());
        if (usergroups.isOk() && usergroups.getUsergroups().size() > 0) {
            UsergroupUsersListResponse response = slack.methods().usergroupUsersList(
                    UsergroupUsersListRequest.builder()
                            .token(token)
                            .includeDisabled(false)
                            .usergroup(usergroups.getUsergroups().get(0).getId())
                            .build());
            assertThat(response.isOk(), is(false));
//            assertThat(response.getError(), is("missing_required_argument"));
            // As of 2018/05, the error message has been changed
            assertThat(response.getError(), is("no_such_subteam"));
        }
    }

    @Test
    public void users_failure() throws Exception {
        UsergroupUsersListResponse response = slack.methods().usergroupUsersList(
                UsergroupUsersListRequest.builder()
                        .token(token)
                        .includeDisabled(false)
                        .usergroup("dummy")
                        .build());
        assertThat(response.isOk(), is(false));
//            assertThat(response.getError(), is("missing_required_argument"));
        // As of 2018/05, the error message has been changed
        assertThat(response.getError(), is("no_such_subteam"));
    }

}