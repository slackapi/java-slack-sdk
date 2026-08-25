package com.slack.api.methods.response.blocks;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BlocksValidateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private ResponseMetadata responseMetadata;
    private List<Error> errors;

    @Data
    public static class Error {
        // The fields below mirror the documented errors[] contract:
        // https://docs.slack.dev/reference/methods/blocks.validate#validation-errors
        // a JSON pointer path to the invalid element (e.g. "/0/text/type")
        private String pointer;
        // the error code (e.g. "failed_constraint")
        private String code;
        // a human-readable description of the issue
        private String message;
        // structured details about what was expected (e.g. {"type": "enum", "expected": ["plain_text", "mrkdwn"]})
        private Constraint constraint;

        @Data
        public static class Constraint {
            private String type;
            private List<String> expected;
        }
    }
}
