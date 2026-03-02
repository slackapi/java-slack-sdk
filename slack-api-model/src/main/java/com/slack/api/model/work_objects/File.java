package com.slack.api.model.work_objects;

import com.slack.api.util.annotation.Required;
import com.slack.api.util.predicate.FieldPredicate;
import lombok.Builder;
import lombok.Value;

import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Predicates.instanceOf;

@Value
@Builder
public class File {
    @Required(validator = FileTypePredicate.class) String type;
    @Required String fileId;

    public static class FileTypePredicate implements FieldPredicate {
        @Override
        public boolean validate(Object obj) {
            return instanceOf(String.class).and(equalTo("file")).test(obj);
        }
    }

    public static FileBuilder builder() {
        return new CustomFileBuilder();
    }

    public static class CustomFileBuilder extends FileBuilder {
        @Override
        public File build() {
            super.type("file");
            return super.build();
        }
    }
}
