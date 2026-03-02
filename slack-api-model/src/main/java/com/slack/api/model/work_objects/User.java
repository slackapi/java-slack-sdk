package com.slack.api.model.work_objects;

import com.slack.api.util.annotation.Required;
import com.slack.api.util.predicate.FieldPredicate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Predicates.instanceOf;
import static com.google.common.base.Predicates.or;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
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
            return instanceOf(String.class).and(or(equalTo("external"), equalTo("slack"))).test(obj);
        }
    }
}
