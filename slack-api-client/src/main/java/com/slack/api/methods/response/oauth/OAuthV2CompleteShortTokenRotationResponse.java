package com.slack.api.methods.response.oauth;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * https://docs.slack.dev/reference/methods/oauth.v2.completeShortTokenRotation
 */
@Data
public class OAuthV2CompleteShortTokenRotationResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String token;
    private ErrorResponseMetadata responseMetadata;
}
