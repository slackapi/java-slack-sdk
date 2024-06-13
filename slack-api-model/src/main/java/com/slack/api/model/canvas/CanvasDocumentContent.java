package com.slack.api.model.canvas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CanvasDocumentContent {
    @Builder.Default
    private String type = "markdown";
    private String markdown;
}
