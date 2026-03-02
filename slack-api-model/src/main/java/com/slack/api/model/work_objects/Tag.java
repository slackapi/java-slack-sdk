package com.slack.api.model.work_objects;

import com.slack.api.model.work_objects.external.TagColor;
import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Tag {
    @Required String text;
    TagColor color;
    String link;
    String iconUrl;
}
