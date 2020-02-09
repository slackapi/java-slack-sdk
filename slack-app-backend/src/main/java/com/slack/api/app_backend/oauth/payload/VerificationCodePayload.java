package com.slack.api.app_backend.oauth.payload;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * https://api.slack.com/docs/oauth
 */
@Data
public class VerificationCodePayload {

    private String code;
    private String state;
    private String error; // "access_denied"

    /**
     * Extracts code and state from Map object. This method is supposed to be used in AWS lambda functions.
     * See also {@link com.slack.api.app_backend.vendor.aws.lambda.request.ApiGatewayRequest}
     */
    public static VerificationCodePayload from(Map<String, List<String >> queryParams) {
        VerificationCodePayload payload = new VerificationCodePayload();
        payload.setCode(queryParams.get("code") != null ? queryParams.get("code").get(0) : null);
        payload.setState(queryParams.get("state") != null ? queryParams.get("state").get(0) : null);
        payload.setError(queryParams.get("error") != null ? queryParams.get("error").get(0) : null);
        return payload;
    }

}
