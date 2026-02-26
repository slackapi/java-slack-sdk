package com.slack.api.model.work_objects;

import com.slack.api.model.work_objects.external.FullSizePreview;
import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkObjectUnfurl {
    @Required private String workObjectType;
    @Required private String externalUrl;
    @Required private String entityId;
    private String relatedConversationsEntityId;
    @Required private String appId;
    private String appName;
    private AppIcons appIcons;
    private String productName;
    private LookupFunction lookupFunction;
    private String authProviderKey;
    private String displayType;
    private Integer ts;
    private Layouts layouts;
    private String workObjectEntityType;
    private FullSizePreview fullSizePreview;
    private String slackFileId;
    private User perspectiveUser;
    private CommentMetadata comments;

    @Data
    @Builder
    public static class CommentMetadata {
        @Required Integer count;
    }
}
