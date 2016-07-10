package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.request.usergroups.UsergroupsCreateRequest;
import com.github.seratch.jslack.api.methods.request.usergroups.UsergroupsListRequest;
import com.github.seratch.jslack.api.methods.request.usergroups.users.UsergroupUsersListRequest;
import com.github.seratch.jslack.api.methods.response.usergroups.UsergroupsCreateResponse;
import com.github.seratch.jslack.api.methods.response.usergroups.UsergroupsListResponse;
import com.github.seratch.jslack.api.methods.response.usergroups.users.UsergroupUsersListResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class Slack_usergroups_Test {

    Slack slack = Slack.getInstance();
    String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");

    @Test
    public void create() throws Exception {
        UsergroupsCreateResponse response = slack.methods().usergroupsCreate(UsergroupsCreateRequest.builder()
                .token(token)
                .name("usergroup-" + System.currentTimeMillis())
                .build());
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is("paid_teams_only"));
    }

    @Test
    public void list() throws Exception {
        UsergroupsListResponse response = slack.methods().usergroupsList(UsergroupsListRequest.builder().token(token).build());
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void users() throws Exception {
        UsergroupUsersListResponse response = slack.methods().usergroupUsersList(
                UsergroupUsersListRequest.builder()
                        .token(token)
                        .usergroup("dummy")
                        .build());
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is("missing_required_argument"));
    }

}