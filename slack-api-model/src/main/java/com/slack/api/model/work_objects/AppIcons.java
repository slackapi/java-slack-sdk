package com.slack.api.model.work_objects;

import com.slack.api.model.block.element.ImageElement;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AppIcons {
    ImageElement image36;
    ImageElement image128;
}
