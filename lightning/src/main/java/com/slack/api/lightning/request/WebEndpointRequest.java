package com.slack.api.lightning.request;

import com.slack.api.lightning.context.WebEndpointContext;
import lombok.ToString;

@ToString(callSuper = true)
public class WebEndpointRequest {

    private String clientIpAddress;

    private final String queryString;
    private final String requestBody;
    private final RequestHeaders headers;

    public WebEndpointRequest(
            String queryString,
            String requestBody,
            RequestHeaders headers) {
        this.queryString = queryString;
        this.requestBody = requestBody;
        this.headers = headers;
    }

    private WebEndpointContext context = new WebEndpointContext();

    public WebEndpointContext getContext() {
        return context;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getRequestBodyAsString() {
        return requestBody;
    }

    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

}
