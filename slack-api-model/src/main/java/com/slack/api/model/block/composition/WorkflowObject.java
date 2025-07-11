package com.slack.api.model.block.composition;

import lombok.*;

import java.util.List;

/**
 * https://docs.slack.dev/reference/block-kit/composition-objects/workflow-object
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowObject {
    private Trigger trigger;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Trigger {
        private String url;
        private List<InputParameter> customizableInputParameters;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class InputParameter {
            private String name;
            private String value;
        }
    }
}
