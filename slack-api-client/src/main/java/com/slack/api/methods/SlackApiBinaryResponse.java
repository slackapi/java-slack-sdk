package com.slack.api.methods;

import java.io.IOException;

public interface SlackApiBinaryResponse extends SlackApiResponse {

    byte[] asBytes() throws IOException;

}
