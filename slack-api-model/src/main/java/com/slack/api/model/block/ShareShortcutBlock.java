package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://api.slack.com/future
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareShortcutBlock implements LayoutBlock {
    public static final String TYPE = "share_shortcut";
    private final String type = TYPE;
    private String blockId;
    private String functionTriggerId;
    private String appId;
    private Boolean isWorkflowApp;
    private List<String> appCollaborators;
    private String buttonLabel;
    private String title;
    private String description;
    private String botUserId;
    private String url;
}
