package com.slack.api.methods;

import java.io.IOException;

/**
 * Some APIs such as admin.analytics.getFile return binary data when the API call is successful.
 */
public interface SlackApiBinaryResponse extends SlackApiResponse {

    byte[] asBytes() throws IOException;

}
