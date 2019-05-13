package com.github.seratch.jslack.api.methods;

import com.github.seratch.jslack.common.json.GsonFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

@Data
@Slf4j
public class SlackApiException extends Exception {

    private final Response response;
    private final String responseBody;
    private final SlackApiErrorResponse error;

    public SlackApiException(Response response, String responseBody) {
        this.response = response;
        this.responseBody = responseBody;
        SlackApiErrorResponse parsedErrorResponse = null;
        try {
            parsedErrorResponse = GsonFactory.createSnakeCase().fromJson(responseBody, SlackApiErrorResponse.class);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                String responseToPrint = responseBody.length() > 1000 ? responseBody.subSequence(0, 1000) + " ..." : responseBody;
                log.debug("Failed to parse the error response body: {}", responseToPrint);
            }
        }
        this.error = parsedErrorResponse;
    }

}
