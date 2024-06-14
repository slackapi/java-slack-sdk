package com.slack.api.methods.request.canvases.sections;

import com.slack.api.methods.SlackApiRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://api.slack.com/methods/canvases.sections.lookup
 */
@Data
@Builder
public class CanvasesSectionsLookupRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private String canvasId;
    private Criteria criteria;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Criteria {
        private List<String> sectionTypes; // "any_header" etc. See CanvasDocumentSectionType
        private String containsText;
    }
}