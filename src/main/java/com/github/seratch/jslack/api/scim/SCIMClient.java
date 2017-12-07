package com.github.seratch.jslack.api.scim;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.scim.request.UsersDeleteRequest;
import com.github.seratch.jslack.api.scim.response.UsersDeleteResponse;

import java.io.IOException;

/**
 * <a href="https://api.slack.com/scim">API Methods</a>
 */
public interface SCIMClient {

    void setEndpointUrlPrefix(String endpointUrlPrefix);

    UsersDeleteResponse delete(UsersDeleteRequest req) throws IOException, SlackApiException;
}
