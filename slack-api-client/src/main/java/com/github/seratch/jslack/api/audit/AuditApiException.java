package com.github.seratch.jslack.api.audit;

import com.github.seratch.jslack.SlackConfig;
import com.github.seratch.jslack.common.json.GsonFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

@Data
@Slf4j
public class AuditApiException extends Exception {

    private final Response response;
    private final String responseBody;
    private final AuditApiErrorResponse error;

    public AuditApiException(Response response, String responseBody) {
        this(SlackConfig.DEFAULT, response, responseBody);
    }

    public AuditApiException(SlackConfig config, Response response, String responseBody) {
        this(response, responseBody, parse(config, responseBody));
    }

    public AuditApiException(Response response, String responseBody, AuditApiErrorResponse error) {
        super(buildErrorMessage(response, error));
        this.response = response;
        this.responseBody = responseBody;
        this.error = error;
    }

    private static String buildErrorMessage(Response response, AuditApiErrorResponse error) {
        String message = "status: " + response.code();
        if (error != null) {
            return message + ", error: " + error.getError() + ", needed: " + error.getNeeded() + ", warning: " + error.getProvided() + ", warning: " + error.getWarning();
        } else {
            return message + ", no response body";
        }
    }

    private static AuditApiErrorResponse parse(SlackConfig config, String responseBody) {
        AuditApiErrorResponse parsedErrorResponse = null;
        try {
            parsedErrorResponse = GsonFactory.createSnakeCase(config).fromJson(responseBody, AuditApiErrorResponse.class);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                String responseToPrint = responseBody.length() > 1000 ? responseBody.subSequence(0, 1000) + " ..." : responseBody;
                log.debug("Failed to parse the error response body: {}", responseToPrint);
            }
        }
        return parsedErrorResponse;
    }

}
