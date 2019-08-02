package com.github.seratch.jslack.app_backend.vendor.aws.lambda.util;

import com.github.seratch.jslack.app_backend.SlackSignature;
import com.github.seratch.jslack.app_backend.vendor.aws.lambda.request.ApiGatewayRequest;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class SlackSignatureVerifier {

    public static final int MAX_TIME_TO_RETAIN_SLACK_REQUEST = 300000; //ms

    private final SlackSignature.Generator signatureGenerator;

    public SlackSignatureVerifier() {
        this(new SlackSignature.Generator());
    }

    public SlackSignatureVerifier(SlackSignature.Generator signatureGenerator) {
        this.signatureGenerator = signatureGenerator;
    }

    public boolean isValid(ApiGatewayRequest request) {
        if (request != null && request.getHeaders() != null) {
            String requestTimestamp = request.getHeaders().get(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP);
            String requestBody = request.getBody();
            String expected = signatureGenerator.generate(requestTimestamp, requestBody);
            String actual = request.getHeaders().get(SlackSignature.HeaderNames.X_SLACK_SIGNATURE);
            return actual != null && expected != null && actual.equals(expected);
        } else {
            return false;
        }
    }

    public boolean isValid(HttpServletRequest request, String requestBody) {
        if (request != null && request.getHeaderNames() != null) {
            String requestTimestamp = request.getHeader(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP);

            Long slackTimestampInMilliSeconds =  Long.parseLong(requestTimestamp) * 1000;
            Long currentTimeInMilliSeconds = System.currentTimeMillis();
            if ( (currentTimeInMilliSeconds - slackTimestampInMilliSeconds) < MAX_TIME_TO_RETAIN_SLACK_REQUEST) {
                String expected = signatureGenerator.generate(requestTimestamp, requestBody);
                String actual = request.getHeader(SlackSignature.HeaderNames.X_SLACK_SIGNATURE);
                return actual != null && expected != null && actual.equals(expected);
            }
            else{
                log.info("REPLAY ATTACK!! Slack message is older than 5 minutes : " + slackTimestampInMilliSeconds + " Vs " + currentTimeInMilliSeconds);
                return false;
            }
        } else {
            return false;
        }
    }

}
