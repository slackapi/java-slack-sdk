package com.slack.api.bolt.aws_lambda.request;

import lombok.Data;

import java.util.Map;

/**
 * The incoming request from AWS API Gateway to a Lambda function.
 * @see <a href="https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-create-api-as-simple-proxy-for-lambda.html">The official tutorial</a>
 */
@Data
public class ApiGatewayRequest {

    private String resource;
    private String path;
    private String httpMethod;
    private Map<String, String> headers;
    private Map<String, String> queryStringParameters;
    private Map<String, String> pathParameters;
    private Map<String, String> stageVariables;
    private RequestContext requestContext;
    private String body;
    private boolean isBase64Encoded;

}
