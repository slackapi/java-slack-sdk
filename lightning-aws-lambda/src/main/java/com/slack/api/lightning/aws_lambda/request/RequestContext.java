package com.slack.api.lightning.aws_lambda.request;

import lombok.Data;

@Data
public class RequestContext {
    private String accountId;
    private String resourceId;
    private String stage;
    private String requestId;
    private Identity identity;
    private String resourcePath;
    private String httpMethod;
    private String apiId;
}
