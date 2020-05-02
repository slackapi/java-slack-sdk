package com.slack.api.status.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class LegacyStatusApiException extends Exception {

    private final Response response;
    private final String responseBody;

    public LegacyStatusApiException(Response response, String responseBody) {
        super("status: " + response.code() + ", message: " + response.message());
        this.response = response;
        this.responseBody = responseBody;
    }

}
