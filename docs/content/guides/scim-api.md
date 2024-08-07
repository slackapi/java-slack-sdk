---
lang: en
---

# SCIM API

[SCIM API](https://api.slack.com/scim) is a set of APIs for provisioning and managing user accounts and groups. SCIM is used by Single Sign-On (SSO) services and identity providers to manage people across a variety of tools, including Slack.

[SCIM (System for Cross-domain Identity Management)](http://www.simplecloud.info/) is supported by myriad services. It behaves slightly differently than other Slack APIs.

Refer to [the API document](https://api.slack.com/scim) for more details.

---
## Call SCIM API in Java

It's straight-forward to call SCIM API using `slack-api-client` library. As with other API clients, creating an API client by invoking a **Slack** object's method.

```java
import com.slack.api.Slack;
import com.slack.api.scim.*;

Slack slack = Slack.getInstance();
String token = System.getenv("SLACK_ADMIN_ACCESS_TOKN"); // `admin` scope required
SCIMClient scim = slack.scim(token);
```

The `SCIMClient` supports all the available endpoints in SCIM APIs. Check [the Javadoc](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/sdkLatestVersion/slack-api-client-sdkLatestVersion-javadoc.jar/!/com/slack/api/scim/SCIMClient.html) for the entire list of them. On this page, let me share a few code snippets demonstrating how to use SCIM APIs in Java.

### Manage Users

The following pieces of Java code demonstrate how to manage users.

```java
import com.slack.api.Slack;
import com.slack.api.scim.*;
import com.slack.api.scim.model.*;
import com.slack.api.scim.response.*;

// Search Users
UsersSearchResponse users = slack.scim(token).searchUsers(req -> req.count(1000));

// Read a User
final String userId = users.getResources().get(0).getId();
UsersReadResponse read = slack.scim(token).readUser(req -> req.id(userId));

// Pagination for Users
UsersSearchResponse users = slack.scim(token).searchUsers(req -> req.count(1).startIndex(2));
users.getItemsPerPage(); // 1
users.getResources().size(); // 1
users.getStartIndex(); // 2

// Create a new User
User newUser = new User();
newUser.setName(new User.Name());
newUser.getName().setGivenName("Kazuhiro");
newUser.getName().setFamilyName("Sera");
// set other fields as well...
UsersCreateResponse creation = slack.scim(token).createUser(req -> req.user(newUser));

// Run a filter query for user search
// https://api.slack.com/scim#filter
UsersSearchResponse searchResp = slack.scim(token).searchUsers(req -> req
  .count(1)
  .filter("userName eq \"" + userName + "\"")
);

// Delete a User
UsersDeleteResponse deletion = slack.scim(token).deleteUser(req -> req.id(userId));
```

### Manage Groups

The following pieces of Java code demonstrate how to manage Groups.

```java
import com.slack.api.scim.request.*;

// Create a Group
Group newGroup = new Group();
newGroup.setDisplayName("Awesome Group");
slack.scim(token).createGroup(req -> req.group(newGroup));

// Search Groups
GroupsSearchResponse groups = slack.scim(token).searchGroups(req -> req.count(1000));

// Pagination
GroupsSearchResponse pagination = slack.scim(token).searchGroups(req -> req.count(1));
pagination.getResources().size(); // 1
pagination.getResources().size(); // 1

// Overwrite values for specified attributes.
GroupsPatchRequest.GroupOperation op = new GroupsPatchRequest.GroupOperation();
GroupsPatchRequest.MemberOperation memberOp = new GroupsPatchRequest.MemberOperation();
User user = slack.scim(token).searchUsers(req -> req.count(1)).getResources().get(0);
memberOp.setValue(user.getId());
memberOp.setOperation("delete");
op.setMembers(Arrays.asList(memberOp));
slack.scim(token).patchGroup(req -> req.id(group.getId()).group(op));

// Overwrite all values for a Group even if some attributes are not given
slack.scim(token).updateGroup(req -> req.id(groupId).group(group));
```

### Handle Errors

`SCIMClient` methods may throw `SCIMApiException` when receiving an unsuccessful response from SCIM API.

```java
// Handling an error
try {
  slack.scim("dummy").searchUsers(req -> req.count(1000));
} catch (SCIMApiException e) {
  e.getMessage(); // "status: 401, description: invalid_authentication"
  e.getResponse(); // HTTP response object (okhttp)
  e.getResponseBody(); // raw string response body
  e.getError(); // deserialized error data in the response body
  e.getError().getErrors().getCode(); // 401
  e.getError().getErrors().getDescription(); // "invalid_authentication"
}
```

---
## Rate Limits

Slack uses rate limits for the SCIM API to help provide a predictably pleasant experience. Please note: unlike many of the other Slack API rate limits, the limits below apply to all SCIM apps in an org, not on a per-app basis. Refer to [the API document](https://api.slack.com/admins/scim#ratelimits) for more details.

`AsyncSCIMClient`, the async client, has great consideration for Rate Limits.

The async client internally has its queue systems to avoid burst traffics as much as possible while `SCIMClient`, the synchronous client, always blindly sends requests. The good thing is that both sync and async clients maintain the metrics data in a `MetricsDatastore` together. This allows the async client to accurately know the current traffic they generated toward the Slack Platform and estimate the remaining amount to call.

The default implementation of the datastore is in-memory one using the JVM heap memory. The default **SlackConfig** enables the in-memory one. It should work nicely for most cases. If your app is fine with it, you don't need to configure anything.

`AsyncSCIMClient` considers the metrics data very well. It may delay API requests to avoid rate-limited errors if the clients in the app already sent too many requests within a short period.

```java
import com.slack.api.Slack;
import com.slack.api.scim.response.*;
import java.util.concurrent.CompletableFuture;

Slack slack = Slack.getInstance();
String token = "xoxp-***"; // Org admin user token

CompletableFuture<UsersSearchResponse> users = slack.scimAsync(token).searchUsers(req -> req
  .startIndex(1)
  .count(100)
  .filter("userName Eq \"Carly\"")
);
```