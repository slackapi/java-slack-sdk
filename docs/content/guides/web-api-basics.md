---
lang: en
---

# Using the Web API

`slack-api-client` contains flexibly configurable HTTP clients for making requests to Slack APIs.

Before trying the samples on this page, you need to set up your Java project first. If you haven't done it yet, check the [API Client Installation](/guides/web-api-client-setup) guide and follow the instructions there.

---
## Initialize Slack Object

Everything in this library starts from a variety of instance methods in `com.slack.api.Slack` class.

```java
import com.slack.api.Slack;

Slack slack = Slack.getInstance();
```

You can access all supported API clients from the facade by following the fluent (in other words, method chaining) interface. For customizing the Slack API clients, initializing `SlackConfig` is also necessary. Consult the section at the bottom of this page for details.

Here is the list of the methods in a `Slack` object to create an API client.

|Method|Return Type|Description|
|-|-|-|
|`Slack#methods(String)`|`com.slack.api.methods.MethodsClient`|Creates a HTTP client for [API Methods](https://docs.slack.dev/reference/methods)|
|`Slack#methodsAsync(String)`|`com.slack.api.methods.AsyncMethodsClient`|Creates an async HTTP client for [API Methods](https://docs.slack.dev/reference/methods) with a great [Rate Limits](https://docs.slack.dev/apis/web-api/rate-limits) supports|
|`Slack#socketMode(String)`|`com.slack.api.socket_mode.SocketModeClient`|Creates a WebSocket client for [Socket Mode](https://docs.slack.dev/apis/events-api/using-socket-mode)|
|`Slack#rtm(String)`|`com.slack.api.rtm.RTMClient`|Creates a WebSocket client for [Real Time Messaging (RTM) API](https://docs.slack.dev/legacy/legacy-rtm-api)|
|`Slack#scim(String)`|`com.slack.api.scim.SCIMClient`|Creates a HTTP client for [SCIM API](https://docs.slack.dev/admins/scim-api)|
|`Slack#audit(String)`|`com.slack.api.audit.AuditClient`|Creates a HTTP client for [Audit Logs API](https://docs.slack.dev/admins/audit-logs-api)|
|`Slack#status()`|`com.slack.api.status.v2.StatusClient`|Creates a HTTP client for [Slack Status API](https://docs.slack.dev/reference/slack-status-api/)|

:::tip

Are you looking for the [Incoming Webhooks](https://docs.slack.dev/messaging/sending-messages-using-incoming-webhooks)? Of course, it's also supported! Check [this guide](/guides/incoming-webhooks) for it.

:::

---
## Call a Method

The most popular Slack Web API method is called [`chat.postMessage`](https://docs.slack.dev/reference/methods/chat.postmessage), and it's used to send a message to a conversation.

To call a Web API method such as [`chat.postMessage`](https://docs.slack.dev/reference/methods/chat.postmessage), a `MethodsClient` instance needs to be initialized with a token. A token usually begins with `xoxb-` (bot token) or `xoxp-` (user token). You get them from each workspace that an app has been installed. [The Slack App configuration pages](https://api.slack.com/apps) help you get your first token for your development workspace.

:::warning 

Hardcoding tokens in your source code is not advised. We highly recommend using env variables or other secure ways to store your tokens to avoid accidental exposures.

:::

```java
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

// Load an env variable
// If the token is a bot token, it starts with `xoxb-` while if it's a user token, it starts with `xoxp-`
String token = System.getenv("SLACK_TOKEN");

// Initialize an API Methods client with the given token
MethodsClient methods = slack.methods(token);

// Build a request object
ChatPostMessageRequest request = ChatPostMessageRequest.builder()
  .channel("#random") // Use a channel ID `C1234567` is preferable
  .text(":wave: Hi from a bot written in Java!")
  .build();

// Get a response as a Java object
ChatPostMessageResponse response = methods.chatPostMessage(request);
```

If everything goes well, you will see a message like this in the `#random` channel in your workspace.

![Web API Hello World Message](/img/web-api-basics-hello-world.png)

To clearly understand what is happening here, take a look at a `curl` command example that is equivalent to the above Java code. The concept behind Slack Web APIs is so straight-forward that it's pretty easy to understand how given parameters will be sent in actual HTTP requests.

```bash
curl -XPOST https://slack.com/api/chat.postMessage \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -H 'Authorization: Bearer xoxb-123-123-abcabcabc' \
  -d 'channel=%23random&text=%3Awave%3A%20Hi%20from%20a%20bot%20written%20in%20Java%21'
```

I believe now you understand what the above Java code actually does!

You might have thought the above Java code still looks a bit redundant. We can simplify it like this. But, if your application is exceedingly sensitive about creating Java objects, reusing `MethodsClient` instances as above may be preferable.

```java
ChatPostMessageResponse response = slack.methods(token).chatPostMessage(ChatPostMessageRequest.builder()
  .channel("#random")
  .text(":wave: Hi from a bot written in Java!")
  .build());
```

It's not over yet, the way to do the same can be much simpler if you use a function argument for initializing a request.

```java
ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
  .channel("#random")
  .text(":wave: Hi from a bot written in Java!"));
```

I'm sure most people should agree the last one is the handiest and concise. We'll use this way for code snippets throughout the rest of this guide.

```java
import com.slack.api.Slack;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

Slack slack = Slack.getInstance();
String token = System.getenv("SLACK_TOKEN");

ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
  .channel("#random")
  .text(":wave: Hi from a bot written in Java!"));
```

If you prefer Kotlin language, your code can be even more concise.

```kotlin
import com.slack.api.Slack

val slack = Slack.getInstance()
val token = System.getenv("SLACK_TOKEN")

val response = slack.methods(token).chatPostMessage { it
  .channel("#random")
  .text(":wave: Hi from a bot written in Kotlin!") 
}
```

In addition, you can check out the [Block Kit Kotlin DSL](/guides/composing-messages#block-kit-kotlin-dsl) for a Kotlin-native way of constructing rich messages.

### Handle Responses

If you're not yet familiar with the Slack Web API response format, read the [Evaluating responses](https://docs.slack.dev/apis/web-api/#responses) guide to understand it. All Web API responses contain a JSON object, which always contains a top-level boolean property `"ok"`, indicating success or failure.

```json
{
  "ok": true,
  "stuff": "This is good"
}
```

```json
{
  "ok": false,
  "error": "something_bad"
}
```

So, you handle responses by evaluating the boolean value returned by `isOk()` method first, then dispatch the operations afterward.

```java
import com.slack.api.model.Message;

ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
  .channel("C1234567")
  .text("Write one, post anywhere"));
if (response.isOk()) {
  Message postedMessage = response.getMessage();
} else {
  String errorCode = response.getError(); // e.g., "invalid_auth", "channel_not_found"
}
```

When calling API methods, errors can occur for a variety of reasons:

1. Received a successful response but in its body, `"ok"` is `false` and an `"error"` such as `channel_not_found` exists. These errors correspond to their definitions on their [method page](https://docs.slack.dev/reference/methods).
2. Got a `java.io.IOException` thrown due to connectivity issues
3. Got a `com.slack.api.methods.SlackApiException` thrown for an unsuccessful response

To understand how to handle `1.` pattern, read [this API document](https://docs.slack.dev/apis/web-api/#responses).

As for `2.` & `3.` patterns, the `MethodsClient` may throw two types of exceptions. Applications are responsible for catching and handling both of these exceptions.

|Exception|Information Included|Reason|
|-|-|-|
|`java.io.IOException`|Has only standard exception information - string message and cause|This exception can be thrown if the request could not be executed due to cancellation, a connectivity problem or timeout.|
|`com.slack.api.methods.SlackApiException`|Has underlying HTTP response, raw string response body, and deserialized `SlackApiErrorResponse` object|This exception can be thrown if Slack API servers respond with an unsuccessful HTTP status code (not 20x).|

The final form of well-considered error handling would be the one below.

```java
import java.io.IOException;
import com.slack.api.methods.SlackApiException;

try {
  ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
    .channel("C1234567")
    .text("Write one, post anywhere"));
  if (response.isOk()) {
    Message postedMessage = response.getMessage();
  } else {
    String errorCode = response.getError(); // e.g., "invalid_auth", "channel_not_found"
  }
} catch (SlackApiException requestFailure) {
  // Slack API responded with unsuccessful status code (= not 20x)  
} catch (IOException connectivityIssue) {
  // Throwing this exception indicates your app or Slack servers had a connectivity issue.
}
```

---
## There's More!

Slack Web API offers [180+ methods](https://docs.slack.dev/reference/methods). The way to use others is almost the same. Just calling methods in `MethodsClient` with a valid token and sufficient parameters works for you.

A good way to check the entire list of methods available in this SDK is to access [the Javadoc](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/sdkLatestVersion/slack-api-client-sdkLatestVersion-javadoc.jar/!/com/slack/api/methods/MethodsClient.html).

#### Call Unsupported Methods

If you need to call a method that `slack-api-client` doesn't support, you can call the method like this.

```java
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiTextResponse;

public class AwesomeMethodResponse implements SlackApiTextResponse {
  private boolean ok;
  private String warning;
  private String error;
  private String needed;
  private String provided;
  private String awesomeness;

  // getter/setter here...
}

Slack slack = Slack.getInstance();

AwesomeMethodResponse response = slack.methods().postFormWithTokenAndParseResponse(
  req -> req.add("user", "U1234567"), // build a request body
  "awesome.method", // the name of the API method
  System.getenv("SLACK_TOKEN"), // a bot token or user token
  AwesomeMethodResponse.class // the response class to bind the response body
);
```

---
## Rate Limits

Slack platform features and APIs rely on [rate limits](https://docs.slack.dev/apis/web-api/rate-limits) to help provide a predictably pleasant experience for users. The limits would be applied on a "per app per workspace" basis. There are several tiers to determine how frequently your apps can call Web APIs. `slack-api-client` has a complete support for those tiers and `AsyncMethodsClient`, the async client, has great consideration for Rate Limits.

The async client internally has its queue systems to avoid burst traffics as much as possible while `MethodsClient`, the synchronous client, always blindly sends requests. The good thing is that both sync and async clients maintain the metrics data in a `MetricsDatastore` together. This allows the async client to accurately know the current traffic they generated toward the Slack Platform and estimate the remaining amount to call.

The default implementation of the datastore is in-memory one using the JVM heap memory. The default `SlackConfig` enables the in-memory one. It should work nicely for most cases. If your app is fine with it, you don't need to configure anything.

```java
import com.slack.api.Slack;
import com.slack.api.SlackConfig;

SlackConfig config = new SlackConfig();
Slack slack = Slack.getInstance(config);
```

`AsyncMethodsClient` considers the metrics data very well. It may delay API requests to avoid rate-limited errors if the clients in the app already sent too many requests within a short period.

```java
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import java.util.concurrent.CompletableFuture;

CompletableFuture<ChatPostMessageResponse> postMessageResult = slack.methodsAsync("xoxb-`*")
  .chatPostMessage(r -> r.channel("C01ABC123").text("This may be delayed a bit"));
```

If your app has multiple nodes, it's also possible to specify the number of nodes.

```java
import com.slack.api.methods.metrics.MemoryMetricsDatastore;

// Give the number of nodes if you run a same app on 3 nodes (default: 1)
config.getMethodsConfig().setMetricsDatastore(new MemoryMetricsDatastore(3));
```

The metrics datastore provides useful information to get along with the Rate Limits. The structure of metrics looks as below. 

The following example is a JSON representation of a `MethodsStats` instance. The stats data is recorded per `MethodsConfig`'s executor name. The metrics and stats support multiple workspaces. So, for most cases, a single stats should be shared among multiple API methods clients. But if your server behaves as multiple Slack apps, it's also possible to have several `MethodsConfig` objects per Slack app.

|Key|Possible Value|
|-|-|
|The Web API executor|Any string - the default is `DEFAULT_SINGLETON_EXECUTOR`|
|Workspace ID|`team_id` (e.g., `T1234567`)|
|`all_completed_calls`|The numbers of all requests per API method|
|`successful_calls`|The numbers of all requests that resulted in `"ok": true` responses per API method|
|`unsuccessful_calls`|The numbers of all requests that resulted in `"ok": false` responses per API method|
|`failed_calls`|The numbers of all requests that resulted in failures (either of `SlackApiException` or `IOException`) per API method|
|`current_queue_size`|The sizes of the current queues per API method|
|`last_minute_requests`|The numbers of all requests in the last 60 seconds per API method|
|`rateLimitedMethods`|Rate limited method names and their epoch time (ms) suggested by Slack API (according to the `retry-after` response header)|

```js
{
  "DEFAULT_SINGLETON_EXECUTOR": {
    "T1234567": {
      "all_completed_calls": {
        "chat.postMessage": 120,
        "users.info": 2,
        "conversations.members": 2
      },
      "successful_calls": {
        "chat.postMessage": 110,
        "users.info": 2,
        "conversations.members": 2
      },
      "unsuccessful_calls": {
        "chat.postMessage": 7
      },
      "failed_calls": {
        "chat.postMessage": 3
      },
      "current_queue_size": {
        "chat.postMessage_C01ABC123": 5,
        "users.info": 0
      },
      "last_minute_requests": {
        "chat.postMessage_C01ABC123": 100,
        "chat.postMessage_C03XYZ555": 3,
        "users.info": 2,
        "conversations.members": 2
      },
      "rateLimitedMethods": {
        "chat.postMessage_C01ABC123": 1582183395064
      }
    }   
  }
}
```

If you don't need the traffic metrics at all, you can disable this feature by the following way:

```java
SlackConfig config = new SlackConfig();
config.setStatsEnabled(false);
Slack slack = Slack.getInstance(config);
```

For more granular control, you can set the same flag for each API client:

```java
SlackConfig config = new SlackConfig();
// Disable the metrics only for Web API methods
config.getMethodsConfig().setStatsEnabled(false);
// For SCIM, Audit Logs APIs, the metrics are still available
Slack slack = Slack.getInstance(config);
```

### Metrics Datastore backed by Redis

If your app wants to have a unified datastore to collect all the metrics across the nodes, we recommend having a Redis cluster for it. It's pretty easy to set up the API client configuration. The following is an example to use a Redis server running on the same host.

```java
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.metrics.RedisMetricsDatastore;
import redis.clients.jedis.JedisPool;

SlackConfig config = new SlackConfig();
// brew install redis
// redis-server /usr/local/etc/redis.conf --loglevel verbose
JedisPool jedis = new JedisPool("localhost");
config.getMethodsConfig().setMetricsDatastore(new RedisMetricsDatastore("test", jedis));

Slack slack = Slack.getInstance(config);
```

---
## Socket Mode

Socket Mode allows your app to use the Events API and interactive components of the platform—without exposing a public HTTP Request URL.

Refer to ["Under the Hood" part in the Bolt document](/guides/socket-mode) for details.

---
## Real Time Messaging (RTM)

:::danger 

The RTM API is not recommended unless your app has unique restrictions, like needs to receive events from behind a firewall.

:::

The Real Time Messaging API is a WebSocket-based API that allows you to receive events from Slack in real-time and send messages as users. It’s sometimes referred to just as the "RTM API”.

Refer to [Real Time Messaging (RTM)](/guides/rtm) for details.

---
## SCIM API

SCIM API is a set of APIs for provisioning and managing user accounts and groups. SCIM is used by Single Sign-On (SSO) services and identity providers to manage people across a variety of tools, including Slack.

Refer to [SCIM API](/guides/scim-api) for details.

---
## Audit Logs API

Audit Logs API is a set of APIs for monitoring what’s happening in your Enterprise Grid organization.

Refer to [Audit Logs API](/guides/audit-logs-api) for details.

---
## Slack Status API

The Slack Status API describes the health of the Slack product. When there’s an incident, outage, or maintenance, the Slack Status API reflects all the information we have on the issue, including which features of Slack are affected and detailed updates over time.

Refer to [Status API](/guides/status-api) for details.

---
## Customize Your Slack API Clients

For customizing Slack API clients, the following options are available in `com.slack.api.SlackConfig`. You can create your own `SlackConfig` with preferred settings and give it to initialize a `Slack` instance.

```java
import com.slack.api.*;

SlackConfig config = new SlackConfig();
config.setPrettyResponseLoggingEnabled(true);
Slack slack = Slack.getInstance(config);
```

Here is the list of available customizable options.

|Name|Type|Description (Default Value)|
|-|-|-|
|`proxyUrl`|`String`|If you enable a proxy server for all outgoing requests to Slack, you can set a single string value representing an absolute URL such as `http://localhost:8888`. (default: null)|
|`prettyResponseLoggingEnabled`|`boolean`|If this flag is set as true, the logger prints the whole response JSON data from Slack APIs in a prettified format. (default: `false`)|
|`failOnUnknownProperties`|`boolean`|If this flag is set as true, JSON parser throws an exception when detecting an unknown property in a Slack API response. (default: `false`)|
|`tokenExistenceVerificationEnabled`|`boolean`|If this flag is set as true, `MethodsClient` throws exceptions when detecting missing token for API calls. (default: `false`)|
|`httpClientResponseHandlers`|`List\<HttpResponseListener\>`|`HttpResponseListener` is a `Consumer\<HttpResponseListener.State\>` function that works as a post-processing hook for Web API calls. To know how to implement it, check the code snippet below. (default: mutable empty list)|
|`auditEndpointUrlPrefix`|`String`|If you need to set a different URL prefix for Audit Logs API calls, you can set the one. (default: `"https://api.slack.com/audit/v1/"`)|
|`methodsEndpointUrlPrefix`|`String`|If you need to set a different URL prefix for API Methods calls, you can set the one. (default: `"https://slack.com/api/"`)|
|`scimEndpointUrlPrefix`|`String`|If you need to set a different URL prefix for SCIM API calls, you can set the one. (default: `"https://api.slack.com/scim/v1/"`)|
|`statusEndpointUrlPrefix`|`String`|If you need to set a different URL prefix for Status API calls, you can set the one. (default: `"https://status.slack.com/api/v2.0.0/"`)|
|`legacyStatusEndpointUrlPrefix`|`String`|If you need to set a different URL prefix for Legacy Status API calls, you can set the one. (default: `"https://status.slack.com/api/v1.0.0/"`)|


### Post-Processing Hooks for Web API Calls

`HttpResponseListener` is a `Consumer` function (in other words, void function) that acts as a post-processing hook for Web API calls. 

The `ResponsePrettyPrintingListener` in this SDK is a good example demonstrating how it works.

The `State` value given to the function holds `SlackConfig` used for the request, the raw string response body, and the whole HTTP response. 

The following listener works only when the `prettyResponseLoggingEnabled` option in the `SlackConfig` is enabled. So, the following code tests the flag in the current config object.

```java
import com.slack.api.SlackConfig;
import com.slack.api.util.json.GsonFactory;
import com.google.gson.*;
import org.slf4j.*;

public class ResponsePrettyPrintingListener extends HttpResponseListener {
  private static final Logger logger = LoggerFactory.getLogger(ResponsePrettyPrintingListener.class);

  @Override
  public void accept(State state) {
    SlackConfig config = state.getConfig();
    String body = state.getParsedResponseBody();
    if (config.isPrettyResponseLoggingEnabled() && body != null && body.trim().startsWith("{")) {
      JsonElement jsonObj = JsonParser.parseString(body);
      String prettifiedJson = GsonFactory.createSnakeCase(config).toJson(jsonObj);
      logger.debug(prettifiedJson);
    }
  }
}
```

Add an instance of this class to enable the hook runtime.

```java
import com.slack.api.*;

SlackConfig config = new SlackConfig();
config.getHttpClientResponseHandlers().add(new ResponsePrettyPrintingListener());
Slack slack = Slack.getInstance(config);
```

That's all about the basics of Slack API clients.

You may encounter some situations more configurable options or flexibilities while making real-world applications. If you have feature requests, go to our [GitHub Issue Tracker](http://github.com/slackapi/java-slack-sdk/issues) and let us hear your voice.
