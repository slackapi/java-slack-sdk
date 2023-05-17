package com.slack.api.scim2;

import com.slack.api.SlackConfig;
import com.slack.api.util.json.GsonFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class SCIM2ApiException extends Exception {

    private final Response response;
    private final String responseBody;
    private final SCIM2ApiErrorResponse error;

    public SCIM2ApiException(Response response, String responseBody) {
        this(SlackConfig.DEFAULT, response, responseBody);
    }

    public SCIM2ApiException(SlackConfig config, Response response, String responseBody) {
        this(response, responseBody, parse(config, responseBody));
    }

    public SCIM2ApiException(Response response, String responseBody, SCIM2ApiErrorResponse error) {
        super(buildErrorMessage(response, error));
        this.response = response;
        this.responseBody = responseBody;
        this.error = error;
    }

    private static String buildErrorMessage(Response response, SCIM2ApiErrorResponse error) {
        String message = "status: " + response.code();
        if (error != null && error.getErrors() != null) {
            return message + ", description: " + error.getErrors().getDescription();
        } else if (error != null && error.getDetail() != null) {
            return message + ", detail: " + error.getDetail();
        } else {
            return message + ", no response body";
        }
    }

    private static SCIM2ApiErrorResponse parse(SlackConfig config, String responseBody) {
        SCIM2ApiErrorResponse parsedErrorResponse = null;
        try {
            parsedErrorResponse = GsonFactory.createCamelCase(config).fromJson(responseBody, SCIM2ApiErrorResponse.class);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                String responseToPrint = responseBody.length() > 1000 ? responseBody.subSequence(0, 1000) + " ..." : responseBody;
                log.debug("Failed to parse the error response body: {}", responseToPrint);
            }
        }
        return parsedErrorResponse;
    }

}
