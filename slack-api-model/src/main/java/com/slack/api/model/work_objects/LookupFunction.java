package com.slack.api.model.work_objects;

import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class LookupFunction {
    @Required String functionId;
    @Required Map<String, Object> inputs;
}
