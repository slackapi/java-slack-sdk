package com.slack.api.model.work_objects;

import com.slack.api.model.work_objects.external.FullSizePreview;
import com.slack.api.model.work_objects.external.WorkObjectEntityType;
import com.slack.api.util.annotation.Required;
import com.slack.api.util.predicate.FieldPredicate;
import com.slack.api.util.predicate.IsValidAppIdPredicate;
import lombok.Builder;
import lombok.Value;

import static com.google.common.base.Predicates.instanceOf;
import static java.util.function.Predicate.isEqual;

@Value
@Builder
public class WorkObjectUnfurl {
    @Required(validator = WorkObjectTypePredicate.class) String workObjectType;
    @Required String externalUrl;
    @Required String entityId;
    String relatedConversationsEntityId;
    @Required(validator = IsValidAppIdPredicate.class) String appId;
    String appName;
    AppIcons appIcons;
    String productName;
    LookupFunction lookupFunction;
    String authProviderKey;
    String displayType;
    Integer ts;
    Layouts layouts;
    WorkObjectEntityType workObjectEntityType;
    FullSizePreview fullSizePreview;
    String slackFileId;
    User perspectiveUser;
    CommentMetadata comments;

    @Value
    @Builder
    public static class CommentMetadata {
        @Required Integer count;
    }

    public static class WorkObjectTypePredicate implements FieldPredicate {
        @Override
        public boolean validate(Object obj) {
            return instanceOf(String.class).and(isEqual("external")).test(obj);
        }
    }
}
