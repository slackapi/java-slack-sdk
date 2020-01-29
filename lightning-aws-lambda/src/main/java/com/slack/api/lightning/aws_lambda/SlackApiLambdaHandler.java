package com.slack.api.lightning.aws_lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.slack.api.lightning.App;
import com.slack.api.lightning.aws_lambda.request.ApiGatewayRequest;
import com.slack.api.lightning.aws_lambda.response.ApiGatewayResponse;
import com.slack.api.lightning.request.Request;
import com.slack.api.lightning.request.RequestHeaders;
import com.slack.api.lightning.response.Response;
import com.slack.api.lightning.util.SlackRequestParser;
import lombok.extern.slf4j.Slf4j;

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
                .queryString(awsReq.getQueryStringParameters())
                .headers(new RequestHeaders(awsReq.getHeaders()))
                .requestBody(awsReq.getBody())
                .remoteAddress(awsReq.getRequestContext().getIdentity() != null ? awsReq.getRequestContext().getIdentity().getSourceIp() : null)
                .build();
        return requestParser.parse(rawRequest);
    }

    protected ApiGatewayResponse toApiGatewayResponse(Response slackResp) {
        return ApiGatewayResponse.builder()
                .statusCode(slackResp.getStatusCode())
                .headers(slackResp.getHeaders())
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

}