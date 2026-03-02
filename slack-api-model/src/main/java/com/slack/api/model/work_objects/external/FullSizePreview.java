package com.slack.api.model.work_objects.external;

import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FullSizePreview {
    @Required Boolean isSupported;
    String previewUrl;
    Boolean isAnimated;
    String width;
    String height;
    String mimeType;
    Error error;

    @Value
    @Builder
    public static class Error {
        @Required String code;
        String message;
    }
}
