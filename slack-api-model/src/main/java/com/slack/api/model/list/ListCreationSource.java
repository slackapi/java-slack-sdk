package com.slack.api.model.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListCreationSource {
    private String type;
    private String referenceId;
    private String workflowFunctionId;
}
