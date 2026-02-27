package com.slack.api.model.work_objects;

import com.slack.api.model.block.ImageBlock;
import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompactLayout {
    public static final String LAYOUT_TYPE = "compact";
    @Required String layoutType;
    ImageBlock productIcon;
    @Required Title title;
    Title subtitle;
    Title headerTitle;
    Title hoverSubtitle;
    Fields fields;
    Integer updatedAt;
}
