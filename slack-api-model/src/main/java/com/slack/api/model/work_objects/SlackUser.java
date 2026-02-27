package com.slack.api.model.work_objects;

import com.slack.api.util.annotation.Required;
import com.slack.api.util.predicate.FieldPredicate;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.regex.Pattern;

@SuperBuilder
@Value
@EqualsAndHashCode(callSuper = true)
public class SlackUser extends User {
    public static final String USER_TYPE = "slack";

    @Required(validator = IsValidSlackUserIdPredicate.class)
    String userId;

    UserMetadata metadata;

    private static final class SlackUserBuilderImpl
            extends SlackUserBuilder<SlackUser, SlackUserBuilderImpl> {
        @Override
        public SlackUser build() {
            this.userType(USER_TYPE);
            return new SlackUser(this);
        }
    }

    public static class IsValidSlackUserIdPredicate implements FieldPredicate {
        private static final Pattern USER_ID_REGEX = Pattern.compile("^[WU][A-Z0-9]{8,}$");

        @Override
        public boolean validate(Object obj) {
            if (!(obj instanceof String)) {
                return false;
            }

            String userId = ((String) obj);
            return USER_ID_REGEX.matcher(userId).matches();
        }
    }
}
