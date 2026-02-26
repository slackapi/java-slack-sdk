package com.slack.api.model.work_objects;

import com.slack.api.model.block.ImageBlock;
import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompactLayout {
    @Required String layoutType;
    private ImageBlock productIcon;
    @Required Title title;
    private Title subtitile;
    private Title headerTitle;
    private Title hoverSubtitle;
    private Fields fields;
    private Integer updatedAt;
}
