package com.slack.api.model.work_objects;

import com.slack.api.model.block.InputBlock;
import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FullLayout {
    public static final String LAYOUT_TYPE = "full";
    @Required String layoutType;
    com.slack.api.model.work_objects.Title headerTitle;
    com.slack.api.model.work_objects.Title headerSubtitle;
    @Required Title title;
    Subtitle subtitle;
    Boolean editable;
    Fields fields;

    public static class Title {
        @Required String text;
        InputBlock input;
    }
}
