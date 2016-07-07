package com.github.seratch.jslack.api.methods.response.api;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class ApiTestResponse implements SlackApiResponse {
    @Data
    public static class Args {
        private String foo;
        private String error;
    }

    private boolean ok;
    private Args args;
    private String warning;
    private String error;

}