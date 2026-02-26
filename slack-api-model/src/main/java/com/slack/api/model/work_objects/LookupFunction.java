package com.slack.api.model.work_objects;

import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class LookupFunction {
    @Required private String functionId;
    @Required private Map<String, Object> inputs;
}
