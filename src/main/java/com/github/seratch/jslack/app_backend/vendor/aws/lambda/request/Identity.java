package com.github.seratch.jslack.app_backend.vendor.aws.lambda.request;

import lombok.Data;

@Data
public class Identity {

    private String cognitoIdentityPoolId;
    private String accountId;
    private String cognitoIdentityId;
    private String caller;
    private String apiKey;
    private String sourceIp;
    private String cognitoAuthenticationType;
    private String cognitoAuthenticationProvider;
    private String userArn;
    private String userAgent;
    private String user;
}
