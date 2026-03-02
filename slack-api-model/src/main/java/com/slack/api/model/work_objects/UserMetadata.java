package com.slack.api.model.work_objects;

import com.slack.api.model.block.ImageBlock;
import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserMetadata {
    String role;
    OverlayIcon overlayIcon;

    @Value
    @Builder
    public static class OverlayIcon {
        @Required String iconName;
        ImageBlock icon;
    }
}
