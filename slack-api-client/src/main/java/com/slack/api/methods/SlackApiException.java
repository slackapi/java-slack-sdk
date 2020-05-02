package com.slack.api.methods;

import com.slack.api.SlackConfig;
import com.slack.api.util.json.GsonFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class SlackApiException extends Exception {

    private final Response response;
    private final String responseBody;
    private final SlackApiErrorResponse error;

    public SlackApiException(Response response, String responseBody) {
        this(SlackConfig.DEFAULT, response, responseBody);
    }

    public SlackApiException(SlackConfig config, Response response, String responseBody) {
        this(response, responseBody, parse(config, responseBody));
    }

    public SlackApiException(Response response, String responseBody, SlackApiErrorResponse error) {
        super(buildErrorMessage(response, error));
        this.response = response;
        this.responseBody = responseBody;
        this.error = error;
    }

    private static String buildErrorMessage(Response response, SlackApiErrorResponse error) {
        String message = "status: " + response.code();
        if (error != null) {
            return message + ", error: " + error.getError() + ", needed: " + error.getNeeded() + ", provided: " + error.getProvided() + ", warning: " + error.getWarning();
        } else {
            return message + ", no response body";
        }
    }

    private static SlackApiErrorResponse parse(SlackConfig config, String responseBody) {
        SlackApiErrorResponse parsedErrorResponse = null;
        try {
            parsedErrorResponse = GsonFactory.createSnakeCase(config).fromJson(responseBody, SlackApiErrorResponse.class);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                String responseToPrint = responseBody.length() > 1000 ? responseBody.subSequence(0, 1000) + " ..." : responseBody;
                log.debug("Failed to parse the error response body: {}", responseToPrint);
            }
        }
        return parsedErrorResponse;
    }


}
