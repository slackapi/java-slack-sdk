package com.slack.api.app_backend.vendor.aws.lambda.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PayloadExtractor {

    /**
     * Extract URL decoded JSON string from body parameter given by AWS Lambda/API Gateway.
     *
     * @param body a string value like "payload=%7B%22type%22%3A%22block_actions%22%2C%22team%22%3A%7B%22id%22%3A% ..."
     * @return
     */
    public String extractPayloadJsonAsString(String body) {
        if (body == null) {
            return null;
        }
        String[] pairs = body.split("\\&");
        for (String pair : pairs) {
            String[] elements = pair.split("=");
            if (elements.length == 2 && elements[0].equals("payload")) {
                try {
                    return URLDecoder.decode(elements[1], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    return elements[1];
                }
            }
        }
        return null;
    }

    public String extractPayloadJsonAsString(ApiGatewayRequest request) {
        if (request == null) {
            return null;
        } else {
            return extractPayloadJsonAsString(request.getBody());
        }
    }

}
