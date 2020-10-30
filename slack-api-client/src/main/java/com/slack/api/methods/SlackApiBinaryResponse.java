package com.slack.api.methods;

public interface SlackApiBinaryResponse extends SlackApiResponse {

    byte[] asBytes();

}
