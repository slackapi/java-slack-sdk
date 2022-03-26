package com.slack.api.methods;

/**
 * A marker interface for Slack API request objects.
 *
 * Developers can instantiate a request object by either of the following ways:
 * <code>
 * AuthTestRequest req = AuthTestRequest.builder().token("your-token").build();
 * AuthTestResponse response = Slack.getInstance().methods().authTest(req);
 * </code>
 * or
 * <code>
 * AuthTestResponse response = Slack.getInstance().methods().authTest(req -> req.token("your-token"));
 * </code>
 * <p>
 * Refer to https://api.slack.com/methods for the API details.
 */
public interface SlackApiRequest {

    String getToken();

}
