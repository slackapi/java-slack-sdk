package com.slack.api.bolt.aws_lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.slack.api.bolt.App;
import com.slack.api.bolt.aws_lambda.request.ApiGatewayRequest;
import com.slack.api.bolt.aws_lambda.response.ApiGatewayResponse;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.SlackRequestParser;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The AWS Lambda handler base class. A sub class that inherits this abstract class works as a Lambda handler.
 *
 * @see <a href="https://aws.amazon.com/lambda/">AWS Lambda</a>
 */
@Slf4j
public abstract class SlackApiLambdaHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

    private final SlackRequestParser requestParser;
    private final App app;

    public SlackApiLambdaHandler(App app) {
        this.app = app;
        this.requestParser = new SlackRequestParser(app.config());
    }

    protected Request<?> toSlackRequest(ApiGatewayRequest awsReq) {
        if (log.isDebugEnabled()) {
            log.debug("AWS API Gateway Request: {}", awsReq);
        }
        SlackRequestParser.HttpRequest rawRequest = SlackRequestParser.HttpRequest.builder()
                .requestUri(awsReq.getPath())
                .queryString(toStringToStringListMap(awsReq.getQueryStringParameters()))
                .headers(new RequestHeaders(toStringToStringListMap(awsReq.getHeaders())))
                .requestBody(awsReq.getBody())
                .remoteAddress(awsReq.getRequestContext().getIdentity() != null ? awsReq.getRequestContext().getIdentity().getSourceIp() : null)
                .build();
        return requestParser.parse(rawRequest);
    }

    protected ApiGatewayResponse toApiGatewayResponse(Response slackResp) {
        return ApiGatewayResponse.builder()
                .statusCode(slackResp.getStatusCode())
                .headers(toStringToStringMap(slackResp.getHeaders()))
                .rawBody(slackResp.getBody())
                .build();
    }

    protected abstract boolean isWarmupRequest(ApiGatewayRequest awsReq);

    @Override
    public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
        if (isWarmupRequest(input)) {
            return ApiGatewayResponse.builder().statusCode(200).build();
        }
        Request<?> req = toSlackRequest(input);
        try {
            return toApiGatewayResponse(app.run(req));
        } catch (Exception e) {
            return ApiGatewayResponse.builder().statusCode(500).rawBody(e.getMessage()).build();
        }
    }

    private static Map<String, String> toStringToStringMap(Map<String, List<String>> stringToStringListMap) {
        Map<String, String> headers = new HashMap<>();
        for (Map.Entry<String, List<String>> each : stringToStringListMap.entrySet()) {
            if (each.getValue() != null && each.getValue().size() > 0) {
                headers.put(each.getKey(), each.getValue().get(0)); // set the first value in the array
            }
        }
        return headers;
    }

    private static Map<String, List<String>> toStringToStringListMap(Map<String, String> stringToStringListMap) {
        if (stringToStringListMap == null) {
            return null;
        }
        Map<String, List<String>> results = new HashMap<>();
        for (Map.Entry<String, String> each : stringToStringListMap.entrySet()) {
            results.put(each.getKey(), Arrays.asList(each.getValue()));
        }
        return results;
    }

}