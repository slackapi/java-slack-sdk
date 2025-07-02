---
lang: en
---

# Web API for Org Admins

The method names of a portion of [API methods](https://docs.slack.dev/reference/methods) start with `admin.`. These APIs are not available for all developers. They are intended to be used by [Enterprise Grid](https://docs.slack.dev/enterprise-grid/) organization administrators. 

---
## Call the Web API for org admins

There is no difference regarding the ways to use those APIs except for necessary scopes.

```java
String orgAdminToken = System.getenv("SLACK_ORG_ADMIN_TOKEN");
Slack slack = Slack.getInstance();

// Reset a user session
AdminUsersSessionResetResponse response = slack.methods(orgAdminToken).adminUsersSessionReset(r -> r
  .userId(userId)
);

// Convert a channel to an Org channel
AdminConversationsSetTeamsResponse orgChannelResp = slack.methods(orgAdminToken).adminConversationsSetTeams(r -> r
  .teamId("T1234567")
  .channelId("C12345567")
  .orgChannel(true)
);

// Slack App Approvals
AdminAppsApprovedListResponse response = slack.methods(orgAdminToken).adminAppsApprovedList(r -> r
  .limit(1000)
  .teamId("T1234567")
);

// There are more...!
```

You can look up the comprehensive list of admin APIs [here](https://docs.slack.dev/admins/). Also, checking [the Javadoc](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/sdkLatestVersion/slack-api-client-sdkLatestVersion-javadoc.jar/!/com/slack/api/methods/MethodsClient.html) and search by a keyword starting with **`admin`** may be helpful to know methods to use.
