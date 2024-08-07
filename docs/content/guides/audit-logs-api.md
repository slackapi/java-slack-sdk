---
lang: en
---

# Audit Logs API

[Audit Logs API](https://api.slack.com/docs/audit-logs-api) is a set of APIs for monitoring what's happening in your [Enterprise Grid](https://api.slack.com/enterprise/grid) organization.

The Audit Logs API can be used by security information and event management (SIEM) tools to provide an analysis of how your Slack organization is being accessed. You can also use this API to write your own applications to see how members of your organization are using Slack.

Follow the instructions in [the API document](https://api.slack.com/docs/audit-logs-api) to get a valid token for using Audit Logs API. Your Slack app for Audit Logs API needs to be installed on the Enterprise Grid Organization, not an individual workspace within the organization.

---
## Call Audit Logs API in Java

It's straight-forward to call Audit Logs API using the `slack-api-client` library.

```java
import com.slack.api.Slack;
import com.slack.api.audit.*;

Slack slack = Slack.getInstance();
String token = System.getenv("SLACK_ADMIN_ACCESS_TOKN"); // `auditlogs:read` scope required
AuditClient audit = slack.audit(token);
```

All the endpoints are supported.

|Java Method|Endpoint|
|-|-|
|`AuditClient#getLogs()`|`GET /logs`|
|`AuditClient#getSchemas()`|`GET /schemas`|
|`AuditClient#getActions()`|`GET /actions`|

### getLogs()

This is the primary endpoint for retrieving actual audit events from your organization. It will return a list of actions that have occurred on the installed workspace or grid organization. Authentication required.

```java
import com.slack.api.audit.response.LogsResponse;

LogsResponse response = audit.getLogs(req -> req
  .oldest(1521214343) // Unix timestamp of the least recent audit event to include (inclusive)
  .action(Actions.User.user_login) // A team member logged in
  .limit(10) // Number of results to optimistically return
);
```

Refer to [Javadoc](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/sdkLatestVersion/slack-api-client-sdkLatestVersion-javadoc.jar/!/com/slack/api/audit/response/LogsResponse.html) to know the response data structure.

### getSchemas()

This endpoint returns information about the kind of objects which the Audit Logs API returns as a list of all objects and a short description. Authentication not required.

```java
import com.slack.api.audit.response.SchemasResponse;

SchemasResponse response = audit.getSchemas();
```

Refer to [Javadoc](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/sdkLatestVersion/slack-api-client-sdkLatestVersion-javadoc.jar/!/com/slack/api/audit/response/SchemasResponse.html) to know the response data structure.

### getActions()

This endpoint returns information about the kind of actions that the Audit Logs API returns as [a list of all actions](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/sdkLatestVersion/slack-api-client-sdkLatestVersion-javadoc.jar/!/com/slack/api/audit/Actions.html) and a short description of each. Authentication not required.

```java
import com.slack.api.audit.response.ActionsResponse;

ActionsResponse response = audit.getActions();
```

Refer to [Javadoc](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/sdkLatestVersion/slack-api-client-sdkLatestVersion-javadoc.jar/!/com/slack/api/audit/response/ActionsResponse.html) to know the response data structure.


---
## Rate Limits

The Audit Logs API methods conform to Slack's [rate limits](https://api.slack.com/docs/rate-limits) and all methods are rated Tier 3. This allows for up to 50 calls per minute, with an allowance for sporadic bursts. Refer to [the API document](https://api.slack.com/admins/audit-logs#how_to_call_the_audit_logs_api) for more details.

`AsyncAuditClient`, the async client, has great consideration for Rate Limits.

The async client internally has its queue systems to avoid burst traffics as much as possible while `AuditClient`, the synchronous client, always blindly sends requests. The good thing is that both sync and async clients maintain the metrics data in a `MetricsDatastore` together. This allows the async client to accurately know the current traffic they generated toward the Slack Platform and estimate the remaining amount to call.

The default implementation of the datastore is in-memory one using the JVM heap memory. The default `SlackConfig` enables the in-memory one. It should work nicely for most cases. If your app is fine with it, you don't need to configure anything.

`AsyncAuditClient` considers the metrics data very well. It may delay API requests to avoid rate-limited errors if the clients in the app already sent too many requests within a short period.

```java
import com.slack.api.audit.*;
import com.slack.api.audit.response.LogsResponse;
import java.util.concurrent.CompletableFuture;

String token = System.getenv("SLACK_ADMIN_ACCESS_TOKN"); // `auditlogs:read` scope required
AsyncAuditClient audit = Slack.getInstance().auditAsync(token);

CompletableFuture<LogsResponse> response = audit.getLogs(req -> req
  .oldest(1521214343) // Unix timestamp of the least recent audit event to include (inclusive)
  .action(Actions.User.user_login) // A team member logged in
  .limit(10) // Number of results to optimistically return
);
```