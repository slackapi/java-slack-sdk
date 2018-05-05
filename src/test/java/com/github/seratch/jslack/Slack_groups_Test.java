package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.request.groups.*;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.groups.*;
import com.github.seratch.jslack.api.model.Group;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class Slack_groups_Test {

    Slack slack = Slack.getInstance();

    @Test
    public void groups() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

        String name = "secret-" + System.currentTimeMillis();
        GroupsCreateResponse creationResponse = slack.methods().groupsCreate(
                GroupsCreateRequest.builder().token(token).name(name).build());
        Group group = creationResponse.getGroup();
        {
            assertThat(creationResponse.isOk(), is(true));
            assertThat(creationResponse.getGroup(), is(notNullValue()));
        }

        {
            GroupsListResponse response = slack.methods().groupsList(
                    GroupsListRequest.builder().token(token).build());
            assertThat(response.isOk(), is(true));
        }
        {
            GroupsListResponse response = slack.methods().groupsList(
                    GroupsListRequest.builder().token(token).excludeArchived(true).build());
            assertThat(response.isOk(), is(true));
        }

        {
            GroupsHistoryResponse response = slack.methods().groupsHistory(
                    GroupsHistoryRequest.builder().token(token).channel(group.getId()).build());
            assertThat(response.isOk(), is(true));
        }

        {
            GroupsInfoResponse response = slack.methods().groupsInfo(
                    GroupsInfoRequest.builder().token(token).channel(group.getId()).build());
            assertThat(response.isOk(), is(true));
        }

        GroupsCreateChildResponse childCreationResponse = slack.methods().groupsCreateChild(
                GroupsCreateChildRequest.builder().token(token).channel(group.getId()).build());
        group = childCreationResponse.getGroup();
        {
            assertThat(childCreationResponse.isOk(), is(true));
        }

        {
            ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(
                    ChatPostMessageRequest.builder().text("hello").channel(childCreationResponse.getGroup().getId()).build());

            String ts = postResponse.getTs();
            GroupsMarkResponse response = slack.methods().groupsMark(
                    GroupsMarkRequest.builder().token(token).channel(group.getId()).ts(ts).build());
            assertThat(response.isOk(), is(true));
        }

        {
            GroupsRenameResponse response = slack.methods().groupsRename(
                    GroupsRenameRequest.builder().token(token).channel(group.getId()).name(name + "-").build());
            assertThat(response.isOk(), is(true));
        }

        {
            GroupsSetPurposeResponse response = slack.methods().groupsSetPurpose(
                    GroupsSetPurposeRequest.builder().token(token).channel(group.getId()).purpose("purpose").build());
            assertThat(response.isOk(), is(true));
        }

        {
            GroupsSetTopicResponse response = slack.methods().groupsSetTopic(
                    GroupsSetTopicRequest.builder().token(token).channel(group.getId()).topic("topic").build());
            assertThat(response.isOk(), is(true));
        }

        {
            GroupsArchiveResponse response = slack.methods().groupsArchive(
                    GroupsArchiveRequest.builder().token(token).channel(group.getId()).build());
            assertThat(response.isOk(), is(true));
        }

        {
            GroupsUnarchiveResponse response = slack.methods().groupsUnarchive(
                    GroupsUnarchiveRequest.builder().token(token).channel(group.getId()).build());
            assertThat(response.isOk(), is(true));
        }

        {
            GroupsCloseResponse response = slack.methods().groupsClose(
                    GroupsCloseRequest.builder().token(token).channel(group.getId()).build());
            assertThat(response.isOk(), is(true));
        }
    }

}