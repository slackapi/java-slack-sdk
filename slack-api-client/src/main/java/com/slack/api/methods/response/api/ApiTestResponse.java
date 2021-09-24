package com.slack.api.methods.response.api;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ApiTestResponse implements SlackApiTextResponse {
    @Data
    public static class Args {
        private String foo;
        private String error;
    }

    private boolean ok;
    private Args args;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

}