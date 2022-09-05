package com.slack.api.webhook;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class WebhookResponse {
    private Integer code;
    private String message;
    private Map<String, List<String>> headers;
    private String body;
}
