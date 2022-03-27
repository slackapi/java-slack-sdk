package com.slack.api.methods;

/**
 * A marker interface for Slack API request objects.
 * <p>
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

    /**
     * Returns a token in this request object.
     * If the API endpoint does not require a token (e.g., api.test), this method can return null.
     *
     * @return token string value
     */
    String getToken();

    /**
     * Updates the token in this request object.
     * <p>
     * The default implementation throws {@link UnsupportedOperationException}.
     * All the built-in implementing classes overrides this method in a proper way.
     *
     * @throws UnsupportedOperationException is always thrown if this method is not overridden
     */
    default void setToken(String token) {
        throw new UnsupportedOperationException(
                "SlackApiRequest#setToken(String) is not overridden in this class.");
    }

}
