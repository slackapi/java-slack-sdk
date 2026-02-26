package com.slack.api.model.work_objects.external;

import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FullSizePreview {
    @Required private Boolean isSupported;
    private String previewUrl;
    private Boolean isAnimated;
    private String width;
    private String height;
    private String mimeType;
    private Error error;

    @Data
    @Builder
    public static class Error {
        @Required private String code;
        private String message;
    }
}
