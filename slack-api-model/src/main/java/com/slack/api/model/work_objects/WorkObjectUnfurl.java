package com.slack.api.model.work_objects;

import com.slack.api.model.work_objects.external.FullSizePreview;
import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WorkObjectUnfurl {
    @Required String workObjectType;
    @Required String externalUrl;
    @Required String entityId;
    String relatedConversationsEntityId;
    @Required String appId;
    String appName;
    AppIcons appIcons;
    String productName;
    LookupFunction lookupFunction;
    String authProviderKey;
    String displayType;
    Integer ts;
    Layouts layouts;
    String workObjectEntityType;
    FullSizePreview fullSizePreview;
    String slackFileId;
    User perspectiveUser;
    CommentMetadata comments;

    @Value
    @Builder
    public static class CommentMetadata {
        @Required Integer count;
    }
}
