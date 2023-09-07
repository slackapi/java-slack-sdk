package com.slack.api.model.admin;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AppWorkflow {
    private String id;
    private String teamId;
    private String workflowFunctionId;
    private String callbackId;
    private String title;
    private String description;
    private Map<String, InputParameter> inputParameters;
    private Map<String, OutputParameter> outputParameters;
    private List<Step> steps;
    private List<String> collaborators;
    private AppIcons icons;
    private Boolean isPublished;
    private String lastUpdatedBy;
    private Integer unpublishedChangeCount;
    private String appId;
    private String source;
    private String billingType;
    private Long dateUpdated;
    private Boolean isBillable;
    private String lastPublishedVersionId;
    private String lastPublishedDate;
    private List<String> triggerIds;
    private Boolean isSalesHomeWorkflow;

    @Data
    public static class Step {
        private String id;
        private String functionId;
        private Map<String, StepInput> inputs;
        private Boolean isPristine;
    }

    @Data
    public static class StepInput {
        private StepInputValue value;
        private boolean hidden;
        private boolean locked;
    }

    @Data
    public static class StepInputValue {
        private String stringValue;
        private List<String> stringValues;
        private List<StepInputValueElement> elements;
        private List<String> required;
        private List<LayoutBlock> interactiveBlocks;
    }

    @Data
    public static class StepInputValueElement {
        private String name;
        private String type;
        private String title;
        private String description;
        @SerializedName("enum")
        private List<String> enumValues;
        private List<Choice> choices;
        @SerializedName("long")
        private Boolean isLong;
        @SerializedName("default")
        private StepInputValueElementDefault defaultValue;
        private Integer maxLength;
        private Integer minLength;
        private Items items;
        private Integer maxItems;
        // TODO: add other properties
    }

    @Data
    public static class StepInputValueElementDefault {
        private String stringValue;
        private List<String> stringValues;
    }

    @Data
    public static class Choice {
        private String title;
        private String value;
        private String description;
    }

    @Data
    public static class Items {
        private String type;
        @SerializedName("enum")
        private List<String> enumValues;
        private List<Choice> choices;
    }

    @Data
    public static class InputParameter {
        private String type;
        private String name;
        private String title;
        private String description;
        private Boolean isRequired;
        private Boolean isHidden;
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
