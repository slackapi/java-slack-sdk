---
lang: en
---

# Status API

The Slack [Status API](https://docs.slack.dev/reference/slack-status-api/) describes the health of the Slack product. When there's an incident, outage, or maintenance, the Slack Status API reflects all the information we have on the issue, including which features of Slack are affected and detailed updates over time.

## Status API in Java

The response data structure coming from Status API is pretty simple. It looks like below in Java.

```java
class CurrentStatus {
  String status;
  String dateCreated;
  String dateUpdated;
  List<SlackIssue> activeIncidents;
}

class SlackIssue {
  Integer id;
  String dateCreated;
  String dateUpdated;
  String title;
  String type;
  String status;
  String url;
  List<String> services;
  List<Note> notes;
}

class Note {
  String dateCreated;
  String body;
}
```

---
## Call current status API in Java

As with other API clients, you can call `Slack`'s method `status()` to create an API client and then call `current()` to perform an HTTP request.

```java
import com.slack.api.Slack;
import com.slack.api.status.v2.model.CurrentStatus;

Slack slack = Slack.getInstance();
CurrentStatus status = slack.status().current();
```

---
## Call history API in Java

Similarly to `current()` above, `history()` performs an HTTP request.

```java
import com.slack.api.Slack;
import com.slack.api.status.v2.StatusClient;
import com.slack.api.status.v2.model.SlackIssue;

Slack slack = Slack.getInstance();
List<SlackIssue> events = slack.status().history();
```
