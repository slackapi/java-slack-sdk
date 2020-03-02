package test_locally.docs;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.admin.apps.AdminAppsApprovedListResponse;
import com.slack.api.methods.response.admin.conversations.AdminConversationsSetTeamsResponse;
import com.slack.api.methods.response.admin.users.AdminUsersSessionResetResponse;

import java.io.IOException;

public class WebApiForAdminsTest {

    public static void main(String[] args) throws IOException, SlackApiException {
        String orgAdminToken = System.getenv("SLACK_ORG_ADMIN_TOKEN");
        Slack slack = Slack.getInstance();

        String userId = "U123";

        {
            // Reset a user session
            AdminUsersSessionResetResponse response = slack.methods(orgAdminToken).adminUsersSessionReset(r -> r
                    .userId(userId)
            );
        }

        {
            // Convert a channel to an Org channel
            AdminConversationsSetTeamsResponse orgChannelResp = slack.methods(orgAdminToken).adminConversationsSetTeams(r -> r
                    .teamId("T1234567")
                    .channelId("C12345567")
                    .orgChannel(true)
            );
        }

        {
            // Slack App Approvals
            AdminAppsApprovedListResponse response = slack.methods(orgAdminToken).adminAppsApprovedList(r -> r
                    .limit(1000)
                    .teamId("T1234567")
            );
        }

        // There are more...!
    }

}
