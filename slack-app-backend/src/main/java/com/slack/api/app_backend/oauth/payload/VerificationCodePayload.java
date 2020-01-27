package com.slack.api.app_backend.oauth.payload;

import lombok.Data;

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
    public static VerificationCodePayload from(Map<String, String> queryStringParameters) {
        VerificationCodePayload payload = new VerificationCodePayload();
        payload.setCode(queryStringParameters.get("code"));
        payload.setState(queryStringParameters.get("state"));
        payload.setError(queryStringParameters.get("error"));
        return payload;
    }

}
