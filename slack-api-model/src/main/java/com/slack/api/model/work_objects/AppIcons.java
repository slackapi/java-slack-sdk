package com.slack.api.model.work_objects;

import com.slack.api.model.block.ImageBlock;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AppIcons {
    ImageBlock image36;
    ImageBlock image128;
}
