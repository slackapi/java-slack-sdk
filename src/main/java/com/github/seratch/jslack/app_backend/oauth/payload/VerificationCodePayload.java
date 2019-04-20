package com.github.seratch.jslack.app_backend.oauth.payload;

import lombok.Data;

import java.util.Map;

@Data
public class VerificationCodePayload {

    private String code;
    private String state;

    /**
     * Extracts code and state from Map object. This method is supposed to be used in AWS lambda functions.
     * See also {@link com.github.seratch.jslack.app_backend.vendor.aws.lambda.request.ApiGatewayRequest}
     */
    public static VerificationCodePayload from(Map<String, String> queryStringParameters) {
        VerificationCodePayload payload = new VerificationCodePayload();
        payload.setCode(queryStringParameters.get("code"));
        payload.setState(queryStringParameters.get("state"));
        return payload;
    }

}
