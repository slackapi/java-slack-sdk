package com.slack.api.model.work_objects;

import com.google.common.base.Preconditions;
import com.slack.api.util.annotation.Required;
import com.slack.api.util.predicate.IsValidUserIdPredicate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import static com.slack.api.util.predicate.IsValidUserIdPredicate.USER_ID_REGEX;

@Value
@EqualsAndHashCode(callSuper = true)
public class SlackUser extends User {
    public static final String USER_TYPE = "slack";
    private static final IsValidUserIdPredicate isValidUserId = new IsValidUserIdPredicate();

    @Required(validator = IsValidUserIdPredicate.class)
    String userId;

    UserMetadata metadata;

    @Builder
    public SlackUser(String userId, UserMetadata metadata) {
        super(USER_TYPE);
        Preconditions.checkArgument(
                isValidUserId.test(userId),
                String.format("Invalid slack userId %s, required format %s", userId, USER_ID_REGEX)
        );
        this.userId = userId;
        this.metadata = metadata;
    }
}
