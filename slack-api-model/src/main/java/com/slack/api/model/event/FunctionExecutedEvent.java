package com.slack.api.model.event;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
public class FunctionExecutedEvent implements Event {

    public static final String TYPE_NAME = "function_executed";

    private final String type = TYPE_NAME;
    private Function function;
    private Map<String, InputValue> inputs;
    private String functionExecutionId;
    private String workflowExecutionId;
    private String eventTs;
    private String botAccessToken;


    @Data
    public static class FunctionParameter {
        private String type; // "string", "number", "slack#/types/user_id"
        private String name;
        private String description;
        private String title;
        @SerializedName("is_required")
        private boolean required;
        private String hint;
        private Integer maximum;
        private Integer minimum;
        @SerializedName("maxLength")
        private Integer maxLength;
        @SerializedName("minLength")
        private Integer minLength;
    }

    @Data
    public static class Function {
        private String id; // "Fn066C7U22JD"
        private String callbackId;
        private String title;
        private String description;
        private String type; // "app"
        private List<FunctionParameter> inputParameters;
        private List<FunctionParameter> outputParameters;
        private String appId;
        private Integer dateCreated;
        private Integer dateUpdated;
        private Integer dateDeleted;
        private boolean formEnabled;
        // TODO: other data patterns
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InputValue {
        private String stringValue;
        private List<String> stringValues;

        public String asString() {
            return this.stringValue;
        }
        public Integer asInteger() {
            return this.stringValue != null ? Integer.valueOf(this.stringValue) : null;
        }
        public Double asDouble() {
            return this.stringValue != null ? Double.valueOf(this.stringValue) : null;
        }
        public Float asFloat() {
            return this.stringValue != null ? Float.valueOf(this.stringValue) : null;
        }
        // TODO: other data patterns
    }
}