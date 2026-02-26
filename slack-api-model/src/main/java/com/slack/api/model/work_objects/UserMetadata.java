package com.slack.api.model.work_objects;

import com.slack.api.model.block.ImageBlock;
import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserMetadata {
    private String role;
    private OverlayIcon overlayIcon;

    @Data
    @Builder
    public static class OverlayIcon {
        @Required private String iconName;
        private ImageBlock icon;
    }
}
