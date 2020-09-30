package com.slack.api.model.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * <pre>
 * {
 *   "title": {
 *     "value": "{{user}} submitted an issue",
 *     "skip_variable_replacement": false,
 *     "variables": {
 *       "user": "David"
 *     }
 *   }
 * }
 * </pre>
 * https://api.slack.com/reference/workflows/workflow_step#input
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowStepInput {

    /**
     * This is the input value. You can use {{variables}}
     * which are included in the view_submission payload from a configuration modal.
     * These variables refer to input from earlier workflow steps.
     */
    private Object value;

    /**
     * Flag to specify if variables in value should be replaced. Default to true.
     */
    private Boolean skipVariableReplacement;

    /**
     * A key-value map of variables to replace
     */
    private Map<String, Object> variables;

}
