package com.slack.api.model.work_objects;

import com.slack.api.util.annotation.Required;
import com.slack.api.util.predicate.FieldPredicate;
import java.util.List;

import lombok.Builder;
import lombok.Value;

import static com.google.common.base.Predicates.instanceOf;
import static com.google.common.base.Predicates.equalTo;

@Value
@Builder
public class Fields {
    @Required(validator = FieldsTypeValuePredicate.class)
    String type;
    @Required List<Field> elements;

    public static class FieldsTypeValuePredicate implements FieldPredicate {
        @Override
        public boolean validate(Object obj) {
            return instanceOf(String.class).and(equalTo("fields")).test(obj);
        }
    }
}
