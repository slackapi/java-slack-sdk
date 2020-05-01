package com.slack.api.status.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

@Data
@Slf4j
public class StatusApiException extends Exception {

    private final Response response;
    private final String responseBody;

    public StatusApiException(Response response, String responseBody) {
        super("status: " + response.code() + ", message: " + response.message());
        this.response = response;
        this.responseBody = responseBody;
    }

}
