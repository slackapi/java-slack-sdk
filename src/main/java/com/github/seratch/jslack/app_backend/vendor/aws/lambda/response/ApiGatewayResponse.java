package com.github.seratch.jslack.app_backend.vendor.aws.lambda.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ApiGatewayResponse {

    private static final Gson GSON = new GsonBuilder().create();

    private final int statusCode;
    private final String body;
    private final Map<String, String> headers;
    private final boolean isBase64Encoded;

    public ApiGatewayResponse(int statusCode, String body, Map<String, String> headers, boolean isBase64Encoded) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
        this.isBase64Encoded = isBase64Encoded;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    // API Gateway expects the property to be called "isBase64Encoded" => isIs
    public boolean isIsBase64Encoded() {
        return isBase64Encoded;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int statusCode = 200;
        private Map<String, String> headers = Collections.emptyMap();
        private String rawBody;
        private Object objectBody;
        private byte[] binaryBody;
        private boolean base64Encoded;

        public Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        @Deprecated
        public Builder setStatusCode(int statusCode) {
            return statusCode(statusCode);
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        @Deprecated
        public Builder setHeaders(Map<String, String> headers) {
            return headers(headers);
        }

        /**
         * Builds the {@link ApiGatewayResponse} using the passed raw body string.
         */
        public Builder rawBody(String rawBody) {
            this.rawBody = rawBody;
            return this;
        }

        @Deprecated
        public Builder setRawBody(String rawBody) {
            return rawBody(rawBody);
        }

        /**
         * Builds the {@link ApiGatewayResponse} using the passed object body
         * converted to JSON.
         */
        public Builder objectBody(Object objectBody) {
            this.objectBody = objectBody;
            return this;
        }

        @Deprecated
        public Builder setObjectBody(Object objectBody) {
            return objectBody(objectBody);
        }

        /**
         * Builds the {@link ApiGatewayResponse} using the passed binary body
         * encoded as base64. {@link #setBase64Encoded(boolean)
         * setBase64Encoded(true)} will be in invoked automatically.
         */
        public Builder binaryBody(byte[] binaryBody) {
            this.binaryBody = binaryBody;
            base64Encoded(true);
            return this;
        }

        @Deprecated
        public Builder setBinaryBody(byte[] binaryBody) {
            return binaryBody(binaryBody);
        }

        /**
         * A binary or rather a base64encoded responses requires
         * <ol>
         * <li>"Binary Media Types" to be configured in API Gateway
         * <li>a request with an "Accept" header set to one of the "Binary Media
         * Types"
         * </ol>
         */
        public Builder base64Encoded(boolean base64Encoded) {
            this.base64Encoded = base64Encoded;
            return this;
        }

        @Deprecated
        public Builder setBase64Encoded(boolean base64Encoded) {
            return base64Encoded(base64Encoded);
        }

        public ApiGatewayResponse build() {
            String body = null;
            if (rawBody != null) {
                body = rawBody;
            } else if (objectBody != null) {
                body = GSON.toJson(objectBody);
            } else if (binaryBody != null) {
                body = new String(Base64.getEncoder().encode(binaryBody), StandardCharsets.UTF_8);
            }
            log.debug("body: {}", body);
            return new ApiGatewayResponse(statusCode, body, headers, base64Encoded);
        }
    }

    // ----------------------

    public static ApiGatewayResponse build302Response(String location) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Location", location);
        return ApiGatewayResponse.builder()
                .setStatusCode(302)
                .setHeaders(headers)
                .build();
    }

}
