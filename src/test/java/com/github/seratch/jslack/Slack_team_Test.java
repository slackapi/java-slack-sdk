package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.request.team.TeamAccessLogsRequest;
import com.github.seratch.jslack.api.methods.request.team.TeamBillableInfoRequest;
import com.github.seratch.jslack.api.methods.request.team.TeamInfoRequest;
import com.github.seratch.jslack.api.methods.request.team.TeamIntegrationLogsRequest;
import com.github.seratch.jslack.api.methods.request.team.profile.TeamProfileGetRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersListRequest;
import com.github.seratch.jslack.api.methods.response.team.TeamAccessLogsResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamBillableInfoResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamInfoResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamIntegrationLogsResponse;
import com.github.seratch.jslack.api.methods.response.team.profile.TeamProfileGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class Slack_team_Test {

    Slack slack = Slack.getInstance();
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void teamAccessLogs() throws Exception {
        TeamAccessLogsResponse response = slack.methods().teamAccessLogs(TeamAccessLogsRequest.builder()
                .token(token)
                .build());
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is("paid_only"));
    }

    @Test
    public void teamBillableInfo() throws Exception {
        String user = slack.methods().usersList(UsersListRequest.builder().token(token).build()).getMembers().get(0).getId();
        TeamBillableInfoResponse response = slack.methods().teamBillableInfo(TeamBillableInfoRequest.builder()
                .token(token)
                .user(user)
                .build());
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamInfo() throws Exception {
        TeamInfoResponse response = slack.methods().teamInfo(TeamInfoRequest.builder()
                .token(token)
                .build());
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamIntegrationLogs() throws Exception {
        String user = slack.methods().usersList(UsersListRequest.builder().token(token).build()).getMembers().get(0).getId();
        TeamIntegrationLogsResponse response = slack.methods().teamIntegrationLogs(TeamIntegrationLogsRequest.builder()
                .token(token)
                .user(user)
                .build());
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamProfileGet() throws Exception {
        TeamProfileGetResponse response = slack.methods().teamProfileGet(TeamProfileGetRequest.builder().token(token).build());
        assertThat(response.isOk(), is(true));
    }

}