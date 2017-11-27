package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.scim.request.UsersDeleteRequest;
import com.github.seratch.jslack.api.scim.response.UsersDeleteResponse;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Slack_scim_Test {

  Slack slack = Slack.getInstance();
  String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");


  @Test
  public void deleteUserTest() throws IOException, SlackApiException {
    final String userId = "1";
    UsersDeleteResponse response = slack.scim().delete(
            UsersDeleteRequest.builder()
                    .token(token)
                    .id(userId)
                    .build());

    assertThat(response.isOk(), is(true));
  }
}
