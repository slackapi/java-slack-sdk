package com.slack.api.model.canvas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CanvasDocumentChange {
    private String operation; // "insert_before" etc. see CanvasEditOperation
    private String sectionId; // "temp:C:AAAAAAAAAAAAAAAAAAAAAAAAAAAA" etc.
    private CanvasDocumentContent documentContent;
}
