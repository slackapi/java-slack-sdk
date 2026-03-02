package com.slack.api.model.work_objects;

import com.google.common.base.Preconditions;
import com.slack.api.model.block.ImageBlock;
import com.slack.api.util.annotation.Required;
import com.slack.api.util.predicate.IsValidAppIdPredicate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EntityReference {
    private static final IsValidAppIdPredicate isValidAppId = new IsValidAppIdPredicate();

    @Required
    String entityId;

    @Required(validator = IsValidAppIdPredicate.class)
    String appId;

    @Required
    String entityUrl;

    String displayType;

    @Required
    String title;

    ImageBlock icon;

    public static class EntityReferenceBuilder {
        public EntityReferenceBuilder appId(String appId) {
            Preconditions.checkArgument(isValidAppId.test(appId));
            this.appId = appId;
            return this;
        }
    }
}
