package com.slack.api.model.work_objects;

import com.slack.api.util.annotation.Required;
import com.slack.api.util.predicate.FieldPredicate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
public abstract class User {
    @Required(validator = IsValidUserTypePredicate.class)
    protected final String userType;

    public boolean isExternalUser() {
        return getUserType().equals("external");
    }

    public boolean isSlackUser() {
        return getUserType().equals("slack");
    }

    public static class IsValidUserTypePredicate implements FieldPredicate {
        @Override
        public boolean validate(Object obj) {
            if (!(obj instanceof String)) {
                return false;
            }

            String userType = (String) obj;
            return userType.equals("external") || userType.equals("slack");
        }
    }
}
