package com.slack.api.model.admin;

import lombok.Data;

import java.util.List;

@Data
public class AppFunction {
    private String id;
    private String appId;
    private String callbackId;
    private String title;
    private String description;
    private String type;
    private List<InputParameter> inputParameters;
    private List<OutputParameter> outputParameters;
    private Long dateCreated;
    private Long dateUpdated;
    private Long dateDeleted;

    @Data
    public static class InputParameter {
        private String type;
        private String name;
        private String title;
        private String description;
        private Boolean isRequired;
    }

    @Data
    public static class OutputParameter {
        private String type;
        private String name;
        private String title;
        private String description;
        private Boolean isRequired;
    }
}
