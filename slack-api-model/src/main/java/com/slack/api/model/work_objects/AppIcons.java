package com.slack.api.model.work_objects;

import com.slack.api.model.block.element.ImageElement;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppIcons {
    private ImageElement image36;
    private ImageElement image128;
}
